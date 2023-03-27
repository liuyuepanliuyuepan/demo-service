package cn.klmb.crm.module.system.controller.admin.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统用户 - 更新
 *
 * @author shilinchuan
 * @date 2022/11/29
 */
@ApiModel(description = "系统用户 - 更新")
@EqualsAndHashCode(callSuper = true)
@Data
public class SysUserUpdateReqVO extends SysUserBaseVO {

    @ApiModelProperty(value = "用户编号", required = true, example = "xxxx")
    @NotNull(message = "用户编号不能为空")
    private String bizId;

}
