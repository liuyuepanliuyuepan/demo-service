package cn.klmb.demo.module.system.service.auth;

import cn.klmb.demo.module.system.controller.admin.auth.vo.SysAuthLoginReqVO;
import cn.klmb.demo.module.system.controller.admin.auth.vo.SysAuthLoginRespVO;
import cn.klmb.demo.module.system.controller.admin.auth.vo.SysAuthMinAppLoginReqVO;
import cn.klmb.demo.module.system.controller.admin.auth.vo.SysAuthWebLoginReqVO;
import cn.klmb.demo.module.system.entity.user.SysUserDO;
import javax.validation.Valid;

/**
 * 管理后台的认证 Service 接口
 *
 * 提供用户的登录、登出的能力
 *
 * @author 快乐萌宝
 */
public interface SysAuthService {

    /**
     * 验证账号 + 密码。如果通过，则返回用户
     *
     * @param username 账号
     * @param password 密码
     * @return 用户
     */
    SysUserDO authenticate(String username, String password);

    /**
     * 账号登录
     *
     * @param reqVO 登录信息
     * @return 登录结果
     */
    SysAuthLoginRespVO login(@Valid SysAuthLoginReqVO reqVO);


    /**
     * 飞书小程序登录
     *
     * @param reqVO 登录信息
     * @return 登录结果
     */
    SysAuthLoginRespVO minAppLogin(@Valid SysAuthMinAppLoginReqVO reqVO);

    /**
     * 飞书网页登录
     *
     * @param reqVO 登录信息
     * @return 登录结果
     */
    SysAuthLoginRespVO webLogin(@Valid SysAuthWebLoginReqVO reqVO);

    /**
     * 基于 token 退出登录
     *
     * @param token token
     */
    void logout(String token);

    /**
     * 刷新访问令牌
     *
     * @param refreshToken 刷新令牌
     * @return 登录结果
     */
    SysAuthLoginRespVO refreshToken(String refreshToken);

}
