package cn.klmb.crm.module.system.controller.admin.sms.vo.template;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统管理 - 短信模板 - 更新
 *
 * @author shilinchuan
 * @date 2022/12/8
 */
@ApiModel(description = "系统管理 - 短信模板 - 更新")
@EqualsAndHashCode(callSuper = true)
@Data
public class SysSmsTemplateUpdateReqVO extends SysSmsTemplateBaseVO {

    @ApiModelProperty(value = "编号", required = true, example = "uuid")
    @NotNull(message = "编号不能为空")
    private String bizId;

}
