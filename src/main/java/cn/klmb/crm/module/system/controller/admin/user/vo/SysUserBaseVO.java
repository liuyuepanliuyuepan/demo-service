package cn.klmb.crm.module.system.controller.admin.user.vo;

import cn.klmb.crm.framework.common.validation.Mobile;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * 系统用户 Base VO，提供给添加、修改、详细的子 VO 使用 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 *
 * @author shilinchuan
 * @date 2022/11/29
 */
@Data
public class SysUserBaseVO {

    @ApiModelProperty(value = "用户账号", required = true)
    @NotBlank(message = "用户账号不能为空")
    @Size(min = 4, max = 30, message = "用户账号长度为 4-30 个字符")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "用户账号由 数字、字母、_ 组成")
    private String username;

    @ApiModelProperty(value = "用户昵称", required = true)
    @Size(max = 30, message = "用户昵称长度不能超过30个字符")
    private String nickname;

    @ApiModelProperty(value = "真实姓名", required = true)
    @Size(max = 30, message = "真实姓名长度不能超过30个字符")
    private String realname;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "部门ID")
    private String deptId;

    @ApiModelProperty(value = "用户邮箱")
    @Email(message = "邮箱格式不正确")
    @Size(max = 50, message = "邮箱长度不能超过 50 个字符")
    private String email;

    @ApiModelProperty(value = "手机号码")
    @Mobile
    private String mobile;

    @ApiModelProperty(value = "用户性别", example = "1", notes = "参见 SexEnum 枚举类")
    private Integer sex;

    @ApiModelProperty(value = "头像地址")
    private String avatar;

    /**
     * 上级ID
     */
    @ApiModelProperty(value = "上级ID")
    private String parentId;

    /**
     * 飞书用户id
     */
    @ApiModelProperty(value = "飞书用户id")
    private String userId;

    /**
     * 飞书用户openId
     */
    @ApiModelProperty(value = "飞书用户openId")
    private String openId;

}
