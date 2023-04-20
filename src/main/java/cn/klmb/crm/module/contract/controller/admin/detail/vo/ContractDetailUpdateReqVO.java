package cn.klmb.crm.module.contract.controller.admin.detail.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;
import javax.validation.constraints.*;

@ApiModel("管理后台 - 合同详情更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ContractDetailUpdateReqVO extends ContractDetailBaseVO {

    @ApiModelProperty(value = "业务id", required = true, example = "cba1c9ca85d54ef2a95e078b55e9dfcc")
    @NotNull(message = "业务id不能为空")
    private String bizId;

}
