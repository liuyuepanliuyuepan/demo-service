package cn.klmb.crm.module.contract.controller.admin.product.vo;

import java.math.BigDecimal;
import lombok.*;
import java.util.*;
import io.swagger.annotations.*;
import cn.klmb.crm.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.klmb.crm.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@ApiModel("管理后台 - 合同产品关系分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ContractProductPageReqVO extends PageParam {

    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @ApiModelProperty(value = "合同ID", example = "9518")
    private String contractId;

    @ApiModelProperty(value = "产品ID", example = "17388")
    private String productId;

    @ApiModelProperty(value = "产品单价")
    private BigDecimal price;

    @ApiModelProperty(value = "销售价格")
    private BigDecimal salesPrice;

    @ApiModelProperty(value = "数量")
    private BigDecimal num;

    @ApiModelProperty(value = "折扣")
    private BigDecimal discount;

    @ApiModelProperty(value = "小计（折扣后价格）")
    private BigDecimal subtotal;

    @ApiModelProperty(value = "单位")
    private String unit;

}
