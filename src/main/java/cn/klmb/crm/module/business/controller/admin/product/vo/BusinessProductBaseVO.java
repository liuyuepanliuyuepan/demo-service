package cn.klmb.crm.module.business.controller.admin.product.vo;

import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 商机产品关系 Base VO，提供给添加、修改、详细的子 VO 使用 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class BusinessProductBaseVO {

    @ApiModelProperty(value = "商机ID")
    private String businessId;

    @ApiModelProperty(value = "产品ID")
    private String productId;

    @ApiModelProperty(value = "产品单价")
    private BigDecimal price;

    @ApiModelProperty(value = "销售价格")
    private BigDecimal salesPrice;

    @ApiModelProperty(value = "数量")
    private BigDecimal num;

    @ApiModelProperty(value = "小计（折扣后价格）")
    private BigDecimal subtotal;

    @ApiModelProperty(value = "折扣")
    private Integer discount;

    @ApiModelProperty(value = "单位")
    private String unit;


}
