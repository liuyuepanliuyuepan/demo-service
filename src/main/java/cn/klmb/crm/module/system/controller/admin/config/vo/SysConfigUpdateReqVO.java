package cn.klmb.crm.module.system.controller.admin.config.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统管理 - 配置管理 - 更新
 *
 * @author shilinchuan
 * @date 2022/12/3
 */
@ApiModel(description = "系统管理 - 配置管理 - 更新")
@EqualsAndHashCode(callSuper = true)
@Data
public class SysConfigUpdateReqVO extends SysConfigBaseVO {

    @ApiModelProperty(value = "参数配置序号", required = true, example = "uuid")
    @NotNull(message = "参数配置编号不能为空")
    private String bizId;

}
