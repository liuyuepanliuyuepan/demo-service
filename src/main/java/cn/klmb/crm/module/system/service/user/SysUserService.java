package cn.klmb.crm.module.system.service.user;

import cn.klmb.crm.framework.base.core.service.KlmbBaseService;
import cn.klmb.crm.module.system.controller.admin.user.vo.SysUserRespVO;
import cn.klmb.crm.module.system.dto.user.SysUserQueryDTO;
import cn.klmb.crm.module.system.entity.user.SysUserDO;
import java.util.List;

/**
 * 系统用户
 *
 * @author shilinchuan
 * @date 2022/11/29
 */
public interface SysUserService extends KlmbBaseService<SysUserDO, SysUserQueryDTO> {

    /**
     * 修改密码
     *
     * @param bizId    用户业务id
     * @param password 密码
     */
    void updatePassword(String bizId, String password);

    /**
     * 重置密码
     *
     * @param bizId 用户业务id
     */
    String resetPassword(String bizId);

    /**
     * 更新状态
     *
     * @param bizId  用户业务id
     * @param status 状态
     */
    void updateStatus(String bizId, Integer status);

    /**
     * 更新用户的最后登陆信息
     *
     * @param bizId   用户编号
     * @param loginIp 登陆 IP
     */
    void updateUserLogin(String bizId, String loginIp);

    /**
     * 通过用户名查询用户
     *
     * @param username 用户名
     * @return 用户对象信息
     */
    SysUserDO findByUsername(String username);

    /**
     * 判断密码是否匹配
     *
     * @param rawPassword     未加密的密码
     * @param encodedPassword 加密后的密码
     * @return 是否匹配
     */
    boolean isPasswordMatch(String rawPassword, String encodedPassword);

    /**
     * 获取当前登录用户部门ID
     *
     * @return 当前登录用户部门ID
     */
    String getLoginUserDeptId();

    /**
     * 查询该用户下级的用户
     *
     * @param userId 用户ID 0代表全部
     * @return data
     */
    List<String> queryChildUserId(String userId);


    /**
     * 根据部门ids查询用户列表
     *
     * @param ids id列表
     * @return data
     */
    List<String> queryUserByDeptIds(List<String> ids);


    /**
     * 根据userId来查询用户详细信息
     *
     * @param userId
     * @return
     */
    SysUserRespVO getUserDetailByUserId(String userId);

    /**
     * 通过用户名查询用户
     *
     * @param realname 用户名
     * @return 用户对象信息
     */
    List<SysUserDO> findByRealname(String realname);

    /**
     * 通过用户名加手机号查询用户
     *
     * @param realname 用户名
     * @param mobile   手机号
     * @return 用户对象信息
     */
    List<SysUserDO> findByRealnameAndMobile(String realname, String mobile);

}

