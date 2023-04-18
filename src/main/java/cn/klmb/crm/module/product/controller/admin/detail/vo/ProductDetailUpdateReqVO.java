package cn.klmb.crm.module.product.controller.admin.detail.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 产品更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProductDetailUpdateReqVO extends ProductDetailBaseVO {

    @ApiModelProperty(value = "业务id", required = true, example = "b2661a0914904461bc856333deff7ba3")
    @NotNull(message = "业务id不能为空")
    private String bizId;

}
