package cn.klmb.crm.module.contract.controller.admin.detail.vo;

import lombok.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import io.swagger.annotations.*;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.klmb.crm.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 合同详情 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class ContractDetailBaseVO {

    @ApiModelProperty(value = "合同名称", example = "李四")
    private String name;

    @ApiModelProperty(value = "客户id", example = "9564")
    private String memberUserId;

    @ApiModelProperty(value = "商机id", example = "21211")
    private String businessId;

    @ApiModelProperty(value = "下单日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime orderDate;

    @ApiModelProperty(value = "负责人ID", example = "31878")
    private String ownerUserId;

    @ApiModelProperty(value = "合同编号")
    private String num;

    @ApiModelProperty(value = "开始时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime startTime;

    @ApiModelProperty(value = "结束时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime endTime;

    @ApiModelProperty(value = "合同金额")
    private BigDecimal money;

    @ApiModelProperty(value = "客户签约人（联系人id）", example = "28944")
    private String contactsId;

    @ApiModelProperty(value = "公司签约人", example = "24482")
    private String companyUserId;

    @ApiModelProperty(value = "备注", example = "朝辞白帝彩云间，千里江陵一日还")
    private String remark;

    @ApiModelProperty(value = "整单折扣")
    private BigDecimal discountRate;

    @ApiModelProperty(value = "产品总金额")
    private BigDecimal totalPrice;

    @ApiModelProperty(value = "合同类型")
    private String types;

    @ApiModelProperty(value = "付款方式", example = "2")
    private String paymentType;

    @ApiModelProperty(value = "最后跟进时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime lastTime;

    @ApiModelProperty(value = "回款总金额")
    private BigDecimal receivedMoney;

    @ApiModelProperty(value = "未回款总金额")
    private BigDecimal unreceivedMoney;

    @ApiModelProperty(value = "未回款总金额-复制使用", example = "2b268b2c7d2f4d4c9b4f03abdb1f082c")
    private String oldBizId;

}
