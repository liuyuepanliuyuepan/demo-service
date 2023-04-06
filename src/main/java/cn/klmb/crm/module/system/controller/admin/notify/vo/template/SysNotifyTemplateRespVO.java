package cn.klmb.crm.module.system.controller.admin.notify.vo.template;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 站内信模板 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SysNotifyTemplateRespVO extends SysNotifyTemplateBaseVO {

    @ApiModelProperty(value = "业务id", required = true, example = "33b9e0e1f1d044078d6c073e08bda391")
    private String bizId;

    @ApiModelProperty(value = "创建时间", required = true)
    private LocalDateTime createTime;

}
