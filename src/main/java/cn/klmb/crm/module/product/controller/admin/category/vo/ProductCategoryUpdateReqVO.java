package cn.klmb.crm.module.product.controller.admin.category.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 产品分类更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProductCategoryUpdateReqVO extends ProductCategoryBaseVO {

    @ApiModelProperty(value = "业务id", required = true, example = "d3fa77161440451e9fd9cc628341afc0")
    @NotNull(message = "业务id不能为空")
    private String bizId;

}
