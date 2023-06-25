package cn.klmb.demo.module.system.controller.admin.notify.vo.message;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 站内信更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SysNotifyMessageUpdateReqVO extends SysNotifyMessageBaseVO {

    @ApiModelProperty(value = "业务id")
    private String bizId;

    @ApiModelProperty(value = "业务ids")
    private List<String> bizIds;

}
