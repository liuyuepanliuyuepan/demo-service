package cn.klmb.crm.module.business.controller.admin.detail.vo;

import static cn.klmb.crm.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 商机 Base VO，提供给添加、修改、详细的子 VO 使用 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class BusinessDetailBaseVO {

    @ApiModelProperty(value = "商机状态(1进行中2赢单3输单4无效)")
    private String businessStatus;

    @ApiModelProperty(value = "下次联系时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime nextTime;

    @ApiModelProperty(value = "客户ID")
    private String customerId;

    @ApiModelProperty(value = "首要联系人ID")
    private String contactsId;

    @ApiModelProperty(value = "预计成交日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime dealDate;

    @ApiModelProperty(value = "跟进状态 0未跟进1已跟进")
    private Integer followup;

    @ApiModelProperty(value = "商机名称")
    private String businessName;

    @ApiModelProperty(value = "商机金额(保留两位小数)")
    private BigDecimal money;

    @ApiModelProperty(value = "整单折扣(保留两位小数)")
    private BigDecimal discountRate;

    @ApiModelProperty(value = "产品总金额(保留两位小数)")
    private BigDecimal totalPrice;

    @ApiModelProperty(value = "备注", example = "朝辞白帝彩云间，千里江陵一日还")
    private String remark;

    @ApiModelProperty(value = "负责人ID")
    private String ownerUserId;

    @ApiModelProperty(value = "最后跟进时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime lastTime;

}
