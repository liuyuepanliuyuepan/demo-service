package cn.klmb.crm.module.system.controller.admin.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * 系统用户 - 重置密码
 *
 * @author shilinchuan
 * @date 2022/11/29
 */
@ApiModel("系统用户 - 重置密码")
@Data
public class SysUserResetPwdReqVO {

    @ApiModelProperty(value = "用户编号", required = true)
    @NotNull(message = "用户编号不能为空")
    private String bizId;

}
