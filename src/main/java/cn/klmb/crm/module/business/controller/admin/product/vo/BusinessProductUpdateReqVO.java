package cn.klmb.crm.module.business.controller.admin.product.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 商机产品关系更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BusinessProductUpdateReqVO extends BusinessProductBaseVO {

    @ApiModelProperty(value = "业务id", required = true, example = "791f03bf205b4f62b22266b156690571")
    @NotNull(message = "业务id不能为空")
    private String bizId;

}
