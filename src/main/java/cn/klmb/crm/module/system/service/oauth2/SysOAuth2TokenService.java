package cn.klmb.crm.module.system.service.oauth2;

import cn.klmb.crm.module.system.entity.oauth2.SysOAuth2AccessTokenDO;
import java.util.List;

/**
 * oauth2令牌 提供访问令牌、刷新令牌的操作
 *
 * @author shilinchuan
 * @date 2022/11/30
 */
public interface SysOAuth2TokenService {

    /**
     * 创建访问令牌 注意：该流程中，会包含创建刷新令牌的创建
     * <p>
     * 参考 DefaultTokenServices 的 createAccessToken 方法
     *
     * @param userId   用户编号
     * @param userType 用户类型
     * @param clientId 客户端编号
     * @param scopes   授权范围
     * @return 访问令牌的信息
     */
    SysOAuth2AccessTokenDO createAccessToken(String userId, Integer userType, String clientId,
            List<String> scopes);

    /**
     * 刷新访问令牌
     * <p>
     * 参考 DefaultTokenServices 的 refreshAccessToken 方法
     *
     * @param refreshToken 刷新令牌
     * @param clientId     客户端编号
     * @return 访问令牌的信息
     */
    SysOAuth2AccessTokenDO refreshAccessToken(String refreshToken, String clientId);

    /**
     * 获得访问令牌
     * <p>
     * 参考 DefaultTokenServices 的 getAccessToken 方法
     *
     * @param accessToken 访问令牌
     * @return 访问令牌的信息
     */
    SysOAuth2AccessTokenDO getAccessToken(String accessToken);

    /**
     * 校验访问令牌
     *
     * @param accessToken 访问令牌
     * @return 访问令牌的信息
     */
    SysOAuth2AccessTokenDO checkAccessToken(String accessToken);

    /**
     * 移除访问令牌 注意：该流程中，会移除相关的刷新令牌
     * <p>
     * 参考 DefaultTokenServices 的 revokeToken 方法
     *
     * @param accessToken 刷新令牌
     * @return 访问令牌的信息
     */
    SysOAuth2AccessTokenDO removeAccessToken(String accessToken);

}

