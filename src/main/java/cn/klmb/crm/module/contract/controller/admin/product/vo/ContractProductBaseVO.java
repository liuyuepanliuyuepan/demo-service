package cn.klmb.crm.module.contract.controller.admin.product.vo;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import io.swagger.annotations.*;
import javax.validation.constraints.*;

/**
 * 合同产品关系 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class ContractProductBaseVO {

    @ApiModelProperty(value = "合同ID", required = true, example = "9518")
    @NotNull(message = "合同ID不能为空")
    private String contractId;

    @ApiModelProperty(value = "产品ID", required = true, example = "17388")
    @NotNull(message = "产品ID不能为空")
    private String productId;

    @ApiModelProperty(value = "产品单价", required = true)
    @NotNull(message = "产品单价不能为空")
    private BigDecimal price;

    @ApiModelProperty(value = "销售价格", required = true)
    @NotNull(message = "销售价格不能为空")
    private BigDecimal salesPrice;

    @ApiModelProperty(value = "数量", required = true)
    @NotNull(message = "数量不能为空")
    private BigDecimal num;

    @ApiModelProperty(value = "折扣", required = true)
    @NotNull(message = "折扣不能为空")
    private BigDecimal discount;

    @ApiModelProperty(value = "小计（折扣后价格）", required = true)
    @NotNull(message = "小计（折扣后价格）不能为空")
    private BigDecimal subtotal;

    @ApiModelProperty(value = "单位", required = true)
    @NotNull(message = "单位不能为空")
    private String unit;

}
