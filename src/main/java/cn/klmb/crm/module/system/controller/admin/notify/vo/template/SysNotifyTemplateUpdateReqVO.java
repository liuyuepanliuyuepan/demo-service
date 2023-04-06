package cn.klmb.crm.module.system.controller.admin.notify.vo.template;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 站内信模板更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SysNotifyTemplateUpdateReqVO extends SysNotifyTemplateBaseVO {

    @ApiModelProperty(value = "业务id", required = true, example = "33b9e0e1f1d044078d6c073e08bda391")
    @NotNull(message = "业务id不能为空")
    private String bizId;

}
