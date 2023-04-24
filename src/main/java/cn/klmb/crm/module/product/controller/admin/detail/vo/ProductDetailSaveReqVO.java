package cn.klmb.crm.module.product.controller.admin.detail.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 产品创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProductDetailSaveReqVO extends ProductDetailBaseVO {

    /**
     * 上下架状态  1上架  0下架
     */
    @ApiModelProperty(value = "上下架状态  1上架  0下架")
    @NotNull(message = "上下架状态不能为空")
    private Integer status;

}
