package cn.klmb.crm.module.product.controller.admin.detail.vo;

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

@ApiModel("管理后台 - 产品分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProductDetailPageReqVO extends PageParam {

    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @ApiModelProperty(value = "产品名称", example = "李四")
    private String name;

    @ApiModelProperty(value = "产品编码")
    private String num;

    @ApiModelProperty(value = "单位")
    private String unit;

    @ApiModelProperty(value = "价格")
    private BigDecimal price;

    @ApiModelProperty(value = "产品分类ID", example = "30930")
    private String categoryId;

    @ApiModelProperty(value = "产品描述", example = "飞流直下三千尺，疑是银河落九天")
    private String description;

    @ApiModelProperty(value = "负责人ID", example = "28479")
    private String ownerUserId;

    @ApiModelProperty(value = "主图")
    private String mainFileIds;

    @ApiModelProperty(value = "详情图")
    private String detailFileIds;

}
