package cn.klmb.crm.module.system.controller.admin.notify.vo.message;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 站内信更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SysNotifyMessageUpdateReqVO extends SysNotifyMessageBaseVO {

    @ApiModelProperty(value = "业务id", required = true, example = "e1cf298d02d2421d8376bf1f4fd988d4")
    @NotNull(message = "业务id不能为空")
    private String bizId;

}
