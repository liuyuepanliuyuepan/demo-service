package cn.klmb.crm.module.system.controller.admin.notify.vo.message;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 站内信 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SysNotifyMessageRespVO extends SysNotifyMessageBaseVO {

    @ApiModelProperty(value = "业务id", required = true, example = "e1cf298d02d2421d8376bf1f4fd988d4")
    private String bizId;

    @ApiModelProperty(value = "创建时间", required = true)
    private LocalDateTime createTime;

}
