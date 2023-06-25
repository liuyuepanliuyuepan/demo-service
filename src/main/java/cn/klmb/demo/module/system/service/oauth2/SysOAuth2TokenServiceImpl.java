package cn.klmb.demo.module.system.service.oauth2;

import static cn.klmb.demo.framework.common.exception.util.ServiceExceptionUtil.exception0;
import static cn.klmb.demo.framework.common.util.collection.CollectionUtils.convertSet;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.klmb.demo.framework.common.exception.enums.GlobalErrorCodeConstants;
import cn.klmb.demo.framework.common.util.date.DateUtils;
import cn.klmb.demo.module.system.dao.oauth2.SysOAuth2AccessTokenMapper;
import cn.klmb.demo.module.system.dao.oauth2.SysOAuth2AccessTokenRedisDAO;
import cn.klmb.demo.module.system.dao.oauth2.SysOAuth2RefreshTokenMapper;
import cn.klmb.demo.module.system.entity.oauth2.SysOAuth2AccessTokenDO;
import cn.klmb.demo.module.system.entity.oauth2.SysOAuth2ClientDO;
import cn.klmb.demo.module.system.entity.oauth2.SysOAuth2RefreshTokenDO;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * oauth2访问 提供访问令牌、刷新令牌的操作
 *
 * @author liuyuepan
 * @date 2022/12/1
 */
@Service
public class SysOAuth2TokenServiceImpl implements SysOAuth2TokenService {

    private final SysOAuth2ClientService sysOAuth2ClientService;
    private final SysOAuth2RefreshTokenMapper sysOAuth2RefreshTokenMapper;
    private final SysOAuth2AccessTokenMapper sysOAuth2AccessTokenMapper;
    private final SysOAuth2AccessTokenRedisDAO sysOAuth2AccessTokenRedisDAO;

    public SysOAuth2TokenServiceImpl(SysOAuth2ClientService sysOAuth2ClientService,
            SysOAuth2RefreshTokenMapper sysOAuth2RefreshTokenMapper,
            SysOAuth2AccessTokenMapper sysOAuth2AccessTokenMapper,
            SysOAuth2AccessTokenRedisDAO sysOAuth2AccessTokenRedisDAO) {
        this.sysOAuth2ClientService = sysOAuth2ClientService;
        this.sysOAuth2RefreshTokenMapper = sysOAuth2RefreshTokenMapper;
        this.sysOAuth2AccessTokenMapper = sysOAuth2AccessTokenMapper;
        this.sysOAuth2AccessTokenRedisDAO = sysOAuth2AccessTokenRedisDAO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysOAuth2AccessTokenDO createAccessToken(String userId, Integer userType,
            String clientId, List<String> scopes) {
        SysOAuth2ClientDO clientDO = sysOAuth2ClientService.validOAuthClient(clientId);
        // 创建刷新令牌
        SysOAuth2RefreshTokenDO refreshTokenDO = createOAuth2RefreshToken(userId, userType,
                clientDO, scopes);
        // 创建访问令牌
        return createOAuth2AccessToken(refreshTokenDO, clientDO);
    }

    @Override
    public SysOAuth2AccessTokenDO refreshAccessToken(String refreshToken, String clientId) {
        // 查询访问令牌
        SysOAuth2RefreshTokenDO refreshTokenDO = sysOAuth2RefreshTokenMapper.selectByRefreshToken(
                refreshToken);
        if (refreshTokenDO == null) {
            throw exception0(GlobalErrorCodeConstants.BAD_REQUEST.getCode(), "无效的刷新令牌");
        }

        // 校验 Client 匹配
        SysOAuth2ClientDO clientDO = sysOAuth2ClientService.validOAuthClient(clientId);
        if (ObjectUtil.notEqual(clientId, refreshTokenDO.getClientId())) {
            throw exception0(GlobalErrorCodeConstants.BAD_REQUEST.getCode(),
                    "刷新令牌的客户端编号不正确");
        }

        // 移除相关的访问令牌
        List<SysOAuth2AccessTokenDO> accessTokenDOs = sysOAuth2AccessTokenMapper.selectListByRefreshToken(
                refreshToken);
        if (CollUtil.isNotEmpty(accessTokenDOs)) {
            sysOAuth2AccessTokenMapper.deleteBatchIds(
                    convertSet(accessTokenDOs, SysOAuth2AccessTokenDO::getId));
            sysOAuth2AccessTokenRedisDAO.deleteList(
                    convertSet(accessTokenDOs, SysOAuth2AccessTokenDO::getAccessToken));
        }

        // 已过期的情况下，删除刷新令牌
        if (DateUtils.isExpired(refreshTokenDO.getExpiresTime())) {
            sysOAuth2RefreshTokenMapper.deleteById(refreshTokenDO.getId());
            throw exception0(GlobalErrorCodeConstants.UNAUTHORIZED.getCode(), "刷新令牌已过期");
        }

        // 创建访问令牌
        return createOAuth2AccessToken(refreshTokenDO, clientDO);
    }

    @Override
    public SysOAuth2AccessTokenDO getAccessToken(String accessToken) {
        // 优先从 Redis 中获取
        SysOAuth2AccessTokenDO accessTokenDO = sysOAuth2AccessTokenRedisDAO.get(accessToken);
        if (accessTokenDO != null) {
            return accessTokenDO;
        }

        // 获取不到，从 MySQL 中获取
        accessTokenDO = sysOAuth2AccessTokenMapper.selectByAccessToken(accessToken);
        // 如果在 MySQL 存在，则往 Redis 中写入
        if (accessTokenDO != null && !DateUtils.isExpired(accessTokenDO.getExpiresTime())) {
            sysOAuth2AccessTokenRedisDAO.set(accessTokenDO);
        }
        return accessTokenDO;
    }

    @Override
    public SysOAuth2AccessTokenDO checkAccessToken(String accessToken) {
        SysOAuth2AccessTokenDO accessTokenDO = getAccessToken(accessToken);
        if (accessTokenDO == null) {
            throw exception0(GlobalErrorCodeConstants.UNAUTHORIZED.getCode(), "访问令牌不存在");
        }
        if (DateUtils.isExpired(accessTokenDO.getExpiresTime())) {
            throw exception0(GlobalErrorCodeConstants.UNAUTHORIZED.getCode(), "访问令牌已过期");
        }
        return accessTokenDO;
    }

    @Override
    public SysOAuth2AccessTokenDO removeAccessToken(String accessToken) {
        // 删除访问令牌
        SysOAuth2AccessTokenDO accessTokenDO = sysOAuth2AccessTokenMapper.selectByAccessToken(
                accessToken);
        if (accessTokenDO == null) {
            return null;
        }
        sysOAuth2AccessTokenMapper.deleteById(accessTokenDO.getId());
        sysOAuth2AccessTokenRedisDAO.delete(accessToken);
        // 删除刷新令牌
        sysOAuth2RefreshTokenMapper.deleteByRefreshToken(accessTokenDO.getRefreshToken());
        return accessTokenDO;
    }

    private SysOAuth2AccessTokenDO createOAuth2AccessToken(SysOAuth2RefreshTokenDO refreshTokenDO,
            SysOAuth2ClientDO clientDO) {
        SysOAuth2AccessTokenDO accessTokenDO = new SysOAuth2AccessTokenDO()
                .setAccessToken(generateAccessToken())
                .setUserId(refreshTokenDO.getUserId())
                .setUserType(refreshTokenDO.getUserType())
                .setClientId(clientDO.getClientId())
                .setScopes(refreshTokenDO.getScopes())
                .setRefreshToken(refreshTokenDO.getRefreshToken())
                .setExpiresTime(
                        LocalDateTime.now().plusSeconds(clientDO.getAccessTokenValiditySeconds()));
        sysOAuth2AccessTokenMapper.insert(accessTokenDO);
        // 记录到 Redis 中
        sysOAuth2AccessTokenRedisDAO.set(accessTokenDO);
        return accessTokenDO;
    }

    private SysOAuth2RefreshTokenDO createOAuth2RefreshToken(String userId, Integer userType,
            SysOAuth2ClientDO clientDO, List<String> scopes) {
        SysOAuth2RefreshTokenDO refreshToken = new SysOAuth2RefreshTokenDO()
                .setRefreshToken(generateRefreshToken())
                .setUserId(userId)
                .setUserType(userType)
                .setClientId(clientDO.getClientId())
                .setScopes(scopes)
                .setExpiresTime(
                        LocalDateTime.now().plusSeconds(clientDO.getRefreshTokenValiditySeconds()));
        sysOAuth2RefreshTokenMapper.insert(refreshToken);
        return refreshToken;
    }

    private static String generateAccessToken() {
        return IdUtil.fastSimpleUUID();
    }

    private static String generateRefreshToken() {
        return IdUtil.fastSimpleUUID();
    }
}