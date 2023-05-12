package cn.klmb.crm.module.contract.controller.admin.product.vo;

import lombok.*;
import java.time.LocalDateTime;
import io.swagger.annotations.*;

@ApiModel("管理后台 - 合同产品关系 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ContractProductRespVO extends ContractProductBaseVO {

    @ApiModelProperty(value = "业务id", required = true, example = "9f8d255c701b40bd8b1686b64223c6aa")
    private String bizId;

    @ApiModelProperty(value = "创建时间", required = true)
    private LocalDateTime createTime;

}
