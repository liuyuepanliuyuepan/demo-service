package cn.klmb.crm.module.business.controller.admin.product.vo;

import static cn.klmb.crm.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

import cn.klmb.crm.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

@ApiModel("管理后台 - 商机产品关系分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BusinessProductPageReqVO extends PageParam {

    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @ApiModelProperty(value = "商机ID", example = "6760")
    private String businessId;

    @ApiModelProperty(value = "产品ID", example = "24931")
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
