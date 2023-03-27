package cn.klmb.crm.module.system.controller.admin.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

/**
 * 系统用户 - 新增
 *
 * @author shilinchuan
 * @date 2022/11/29
 */
@ApiModel(description = "系统用户 - 新增")
@EqualsAndHashCode(callSuper = true)
@Data
public class SysUserSaveReqVO extends SysUserBaseVO {

    @ApiModelProperty(value = "密码", required = true)
    @NotEmpty(message = "密码不能为空")
    @Length(min = 4, max = 16, message = "密码长度为 4-16 位")
    private String password;

}
