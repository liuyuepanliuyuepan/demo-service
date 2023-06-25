package cn.klmb.demo.module.system.service.auth;

import static cn.klmb.demo.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.klmb.demo.module.system.enums.ErrorCodeConstants.AUTH_LOGIN_BAD_CREDENTIALS;
import static cn.klmb.demo.module.system.enums.ErrorCodeConstants.AUTH_LOGIN_USER_DISABLED;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.klmb.demo.framework.common.enums.CommonStatusEnum;
import cn.klmb.demo.framework.common.enums.UserTypeEnum;
import cn.klmb.demo.framework.common.util.servlet.ServletUtils;
import cn.klmb.demo.module.system.controller.admin.auth.vo.SysAuthLoginReqVO;
import cn.klmb.demo.module.system.controller.admin.auth.vo.SysAuthLoginRespVO;
import cn.klmb.demo.module.system.controller.admin.auth.vo.SysAuthMinAppLoginReqVO;
import cn.klmb.demo.module.system.controller.admin.auth.vo.SysAuthWebLoginReqVO;
import cn.klmb.demo.module.system.controller.admin.user.vo.SysUserRespVO;
import cn.klmb.demo.module.system.convert.auth.SysAuthConvert;
import cn.klmb.demo.module.system.convert.user.SysUserConvert;
import cn.klmb.demo.module.system.dto.feishu.FeishuMinAppResultDTO;
import cn.klmb.demo.module.system.dto.feishu.FeishuWebResultDTO;
import cn.klmb.demo.module.system.entity.dept.SysDeptDO;
import cn.klmb.demo.module.system.entity.logger.SysLoginLogDO;
import cn.klmb.demo.module.system.entity.oauth2.SysOAuth2AccessTokenDO;
import cn.klmb.demo.module.system.entity.user.SysUserDO;
import cn.klmb.demo.module.system.enums.ErrorCodeConstants;
import cn.klmb.demo.module.system.enums.logger.LoginLogTypeEnum;
import cn.klmb.demo.module.system.enums.logger.LoginResultEnum;
import cn.klmb.demo.module.system.enums.oauth2.SysOAuth2ClientConstants;
import cn.klmb.demo.module.system.manager.SysFeishuManager;
import cn.klmb.demo.module.system.service.dept.SysDeptService;
import cn.klmb.demo.module.system.service.logger.SysLoginLogService;
import cn.klmb.demo.module.system.service.oauth2.SysOAuth2TokenService;
import cn.klmb.demo.module.system.service.user.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.util.Objects;
import org.springframework.stereotype.Service;


/**
 * 管理后台的认证 Service 接口
 * <p>
 * 提供用户的登录、登出的能力
 *
 * @author liuyuepan
 * @date 2022/12/1
 */
@Service
public class SysAuthServiceImpl implements SysAuthService {

    private final SysUserService sysUserService;
    private final SysOAuth2TokenService sysOAuth2TokenService;
    private final SysLoginLogService sysLoginLogService;

    private final SysFeishuManager sysFeishuManager;

    private final SysDeptService sysDeptService;

    public SysAuthServiceImpl(SysUserService sysUserService,
            SysOAuth2TokenService sysOAuth2TokenService, SysLoginLogService sysLoginLogService,
            SysFeishuManager sysFeishuManager, SysDeptService sysDeptService) {
        this.sysUserService = sysUserService;
        this.sysOAuth2TokenService = sysOAuth2TokenService;
        this.sysLoginLogService = sysLoginLogService;
        this.sysFeishuManager = sysFeishuManager;
        this.sysDeptService = sysDeptService;
    }

    @Override
    public SysUserDO authenticate(String username, String password) {
        final LoginLogTypeEnum logTypeEnum = LoginLogTypeEnum.LOGIN_USERNAME;
        // 校验账号是否存在
        SysUserDO user = sysUserService.findByUsername(username);
        if (user == null) {
            createLoginLog(null, username, logTypeEnum, LoginResultEnum.BAD_CREDENTIALS);
            throw exception(AUTH_LOGIN_BAD_CREDENTIALS);
        }
        if (!sysUserService.isPasswordMatch(password, user.getPassword())) {
            createLoginLog(user.getBizId(), username, logTypeEnum, LoginResultEnum.BAD_CREDENTIALS);
            throw exception(AUTH_LOGIN_BAD_CREDENTIALS);
        }
        // 校验是否禁用
        if (ObjectUtil.notEqual(user.getStatus(), CommonStatusEnum.ENABLE.getStatus())) {
            createLoginLog(user.getBizId(), username, logTypeEnum, LoginResultEnum.USER_DISABLED);
            throw exception(AUTH_LOGIN_USER_DISABLED);
        }
        return user;
    }

    @Override
    public SysAuthLoginRespVO login(SysAuthLoginReqVO reqVO) {
        // 使用账号密码，进行登录
        SysUserDO user = authenticate(reqVO.getUsername(), reqVO.getPassword());

        // 创建 Token 令牌，记录登录日志
        SysAuthLoginRespVO tokenAfterLoginSuccess = createTokenAfterLoginSuccess(user.getBizId(),
                reqVO.getUsername(),
                LoginLogTypeEnum.LOGIN_USERNAME);
        tokenAfterLoginSuccess.setUserInfo(SysUserConvert.INSTANCE.convert01(user));
        SysUserRespVO userInfo = tokenAfterLoginSuccess.getUserInfo();
        if (ObjectUtil.isNotNull(userInfo) && StrUtil.isNotBlank(userInfo.getDeptId())) {
            SysDeptDO sysDeptDO = sysDeptService.getByBizId(userInfo.getDeptId());
            userInfo.setDeptName(ObjectUtil.isNotNull(sysDeptDO) ? sysDeptDO.getName() : null);
            userInfo.setEmail(userInfo.getEmail());
        }
        return tokenAfterLoginSuccess;
    }

    @Override
    public SysAuthLoginRespVO minAppLogin(SysAuthMinAppLoginReqVO reqVO) {
        FeishuMinAppResultDTO feishuMinAppResultDTO = sysFeishuManager.code2session(
                reqVO.getCode());
        if (ObjectUtil.isNull(feishuMinAppResultDTO)) {
            throw exception(ErrorCodeConstants.USER_NOT_EXISTS);
        }
        String employeeId = feishuMinAppResultDTO.getEmployee_id();
        String openId = feishuMinAppResultDTO.getOpen_id();
        SysUserDO sysUserDO = sysUserService.getOne(
                new LambdaQueryWrapper<SysUserDO>().eq(SysUserDO::getFsUserId, employeeId)
                        .eq(SysUserDO::getDeleted, false));
        if (ObjectUtil.isNull(sysUserDO)) {
            throw exception(ErrorCodeConstants.USER_NOT_EXISTS);
        }
        if (StrUtil.isBlank(sysUserDO.getOpenId()) && StrUtil.isNotBlank(openId)) {
            sysUserDO.setOpenId(openId);
            sysUserService.updateDO(sysUserDO);
        }
        // 创建 Token 令牌，记录登录日志
        SysAuthLoginRespVO tokenAfterLoginSuccess = createTokenAfterLoginSuccess(
                sysUserDO.getBizId(),
                sysUserDO.getUsername(),
                LoginLogTypeEnum.LOGIN_FEISHU_MIN_APP);
        tokenAfterLoginSuccess.setUserInfo(SysUserConvert.INSTANCE.convert01(sysUserDO));
        SysUserRespVO userInfo = tokenAfterLoginSuccess.getUserInfo();
        if (ObjectUtil.isNotNull(userInfo) && StrUtil.isNotBlank(userInfo.getDeptId())) {
            SysDeptDO sysDeptDO = sysDeptService.getByBizId(userInfo.getDeptId());
            userInfo.setDeptName(ObjectUtil.isNotNull(sysDeptDO) ? sysDeptDO.getName() : null);
            userInfo.setEmail(userInfo.getEmail());
        }
        return tokenAfterLoginSuccess;

    }

    @Override
    public SysAuthLoginRespVO webLogin(SysAuthWebLoginReqVO reqVO) {
        FeishuWebResultDTO feishuWebResultDTO = sysFeishuManager.authenV1AccessToken(
                reqVO.getCode());
        if (ObjectUtil.isNull(feishuWebResultDTO)) {
            throw exception(ErrorCodeConstants.USER_NOT_EXISTS);
        }
        String userId = feishuWebResultDTO.getUser_id();
        String openId = feishuWebResultDTO.getOpen_id();
        SysUserDO sysUserDO = sysUserService.getOne(
                new LambdaQueryWrapper<SysUserDO>().eq(SysUserDO::getFsUserId, userId)
                        .eq(SysUserDO::getDeleted, false));
        if (ObjectUtil.isNull(sysUserDO)) {
            throw exception(ErrorCodeConstants.USER_NOT_EXISTS);
        }
        if (StrUtil.isBlank(sysUserDO.getOpenId()) && StrUtil.isNotBlank(openId)) {
            sysUserDO.setOpenId(openId);
            sysUserService.updateDO(sysUserDO);
        }
        // 创建 Token 令牌，记录登录日志
        SysAuthLoginRespVO tokenAfterLoginSuccess = createTokenAfterLoginSuccess(
                sysUserDO.getBizId(),
                sysUserDO.getUsername(),
                LoginLogTypeEnum.LOGIN_FEISHU_WEB);
        tokenAfterLoginSuccess.setUserInfo(SysUserConvert.INSTANCE.convert01(sysUserDO));
        SysUserRespVO userInfo = tokenAfterLoginSuccess.getUserInfo();
        if (ObjectUtil.isNotNull(userInfo) && StrUtil.isNotBlank(userInfo.getDeptId())) {
            SysDeptDO sysDeptDO = sysDeptService.getByBizId(userInfo.getDeptId());
            userInfo.setDeptName(ObjectUtil.isNotNull(sysDeptDO) ? sysDeptDO.getName() : null);
            userInfo.setEmail(userInfo.getEmail());

        }
        return tokenAfterLoginSuccess;
    }


    @Override
    public void logout(String token) {
        // 删除访问令牌
        sysOAuth2TokenService.removeAccessToken(token);
    }

    @Override
    public SysAuthLoginRespVO refreshToken(String refreshToken) {
        SysOAuth2AccessTokenDO accessTokenDO = sysOAuth2TokenService.refreshAccessToken(
                refreshToken, SysOAuth2ClientConstants.CLIENT_ID_DEFAULT);
        return SysAuthConvert.INSTANCE.convert(accessTokenDO);
    }

    private SysAuthLoginRespVO createTokenAfterLoginSuccess(String userId, String username,
            LoginLogTypeEnum logType) {
        // 插入登陆日志
        createLoginLog(userId, username, logType, LoginResultEnum.SUCCESS);
        // 创建访问令牌
        SysOAuth2AccessTokenDO accessTokenDO = sysOAuth2TokenService.createAccessToken(userId,
                getUserType().getValue(), SysOAuth2ClientConstants.CLIENT_ID_DEFAULT, null);
        // 构建返回结果
        return SysAuthConvert.INSTANCE.convert(accessTokenDO);
    }

    private UserTypeEnum getUserType() {
        return UserTypeEnum.ADMIN;
    }

    private void createLoginLog(String userId, String username,
            LoginLogTypeEnum logTypeEnum, LoginResultEnum loginResult) {
        // 插入登录日志
        SysLoginLogDO saveDO = SysLoginLogDO.builder()
                .logType(logTypeEnum.getType())
//                .traceId()
                .userId(userId)
                .userType(getUserType().getValue())
                .username(username)
                .userAgent(ServletUtils.getUserAgent())
                .userIp(ServletUtils.getClientIP())
                .result(loginResult.getResult())
                .build();
        sysLoginLogService.saveDO(saveDO);
        // 更新最后登录时间
        if (userId != null && Objects.equals(LoginResultEnum.SUCCESS.getResult(),
                loginResult.getResult())) {
            sysUserService.updateUserLogin(userId, ServletUtils.getClientIP());
        }
    }
}