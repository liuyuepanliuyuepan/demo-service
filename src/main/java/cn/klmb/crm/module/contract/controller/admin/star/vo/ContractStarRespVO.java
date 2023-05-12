package cn.klmb.crm.module.contract.controller.admin.star.vo;

import lombok.*;
import java.time.LocalDateTime;
import io.swagger.annotations.*;

@ApiModel("管理后台 - 合同标星关系 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ContractStarRespVO extends ContractStarBaseVO {

    @ApiModelProperty(value = "业务id", required = true, example = "bf2548f33971450c8f5c980e153982a2")
    private String bizId;

    @ApiModelProperty(value = "创建时间", required = true)
    private LocalDateTime createTime;

}
