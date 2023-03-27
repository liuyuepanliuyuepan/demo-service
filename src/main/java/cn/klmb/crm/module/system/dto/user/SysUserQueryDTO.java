package cn.klmb.crm.module.system.dto.user;

import cn.klmb.crm.framework.base.core.annotations.DtoFieldQuery;
import cn.klmb.crm.framework.base.core.annotations.DtoFieldQuery.Operator;
import cn.klmb.crm.framework.base.core.pojo.KlmbBaseQueryDTO;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 系统用户
 *
 * @author shilinchuan
 * @date 2022/11/29
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class SysUserQueryDTO extends KlmbBaseQueryDTO {

    /**
     * 用户账号
     */
    @DtoFieldQuery
    private String username;

    /**
     * 密码
     */
    @DtoFieldQuery
    private String password;

	/**
	 * 用户昵称
     */
    @DtoFieldQuery(queryType = Operator.LIKE)
    private String nickname;

    /**
     * 真实姓名
     */
    @DtoFieldQuery(queryType = Operator.LIKE)
    private String realname;

	/**
     * 备注
     */
    @DtoFieldQuery
    private String remark;

	/**
	 * 部门ID
     */
    @DtoFieldQuery
    private String deptId;

	/**
	 * 用户邮箱
     */
    @DtoFieldQuery
    private String email;

	/**
	 * 手机号码
     */
    @DtoFieldQuery(queryType = Operator.LIKE)
    private String mobile;

	/**
	 * 用户性别
     */
    @DtoFieldQuery
    private Integer sex;

	/**
	 * 头像地址
     */
    @DtoFieldQuery
    private String avatar;

	/**
	 * 帐号状态（1正常 2停用）
     */
    @DtoFieldQuery
    private Integer status;

	/**
     * 最后登录IP
     */
    @DtoFieldQuery
    private String loginIp;

    /**
     * 最后登录时间
     */
    @DtoFieldQuery
    private LocalDateTime loginDate;

    /**
     * 角色ID列表
     */
    private List<String> roleIds;

}
