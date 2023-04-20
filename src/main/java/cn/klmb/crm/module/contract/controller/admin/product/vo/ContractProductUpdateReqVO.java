package cn.klmb.crm.module.contract.controller.admin.product.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;
import javax.validation.constraints.*;

@ApiModel("管理后台 - 合同产品关系更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ContractProductUpdateReqVO extends ContractProductBaseVO {

    @ApiModelProperty(value = "业务id", required = true, example = "9f8d255c701b40bd8b1686b64223c6aa")
    @NotNull(message = "业务id不能为空")
    private String bizId;

}
