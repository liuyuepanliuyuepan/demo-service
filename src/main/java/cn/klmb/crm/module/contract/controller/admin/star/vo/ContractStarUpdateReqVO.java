package cn.klmb.crm.module.contract.controller.admin.star.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;
import javax.validation.constraints.*;

@ApiModel("管理后台 - 合同标星关系更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ContractStarUpdateReqVO extends ContractStarBaseVO {

    @ApiModelProperty(value = "业务id", required = true, example = "bf2548f33971450c8f5c980e153982a2")
    @NotNull(message = "业务id不能为空")
    private String bizId;

}
