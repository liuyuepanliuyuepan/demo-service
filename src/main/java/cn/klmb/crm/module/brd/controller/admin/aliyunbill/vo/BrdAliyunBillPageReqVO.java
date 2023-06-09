package cn.klmb.crm.module.brd.controller.admin.aliyunbill.vo;

import java.math.BigDecimal;
import lombok.*;
import java.util.*;
import io.swagger.annotations.*;
import cn.klmb.crm.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.klmb.crm.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@ApiModel("管理后台 - 博瑞迪阿里云账单分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BrdAliyunBillPageReqVO extends PageParam {

	@ApiModelProperty(value = "创建时间")
	@DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
	private LocalDateTime[] createTime;

	@ApiModelProperty(value = "账期")
	private String billingCycle;

	@ApiModelProperty(value = "账单日期")
	@DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
	private String billingDate;

	@ApiModelProperty(value = "账单类型：•	SubscriptionOrder （预付订单）。•	PayAsYouGoBill （后付账单）。•	Refund （退款）。•	Adjustment （调账） ")
	private String item;

	@ApiModelProperty(value = "实例ID", example = "12544")
	private String instanceId;

	@ApiModelProperty(value = "币种，取值：•	CNY。•	USD。•	JPY。 ")
	private String currency;

	@ApiModelProperty(value = "订阅类型，取值：•	Subscription：预付费。•	PayAsYouGo：后付费。 ", example = "1")
	private String subscriptionType;

	@ApiModelProperty(value = "实例详情配置")
	private String instanceConfig;

	@ApiModelProperty(value = "现金支付")
	private BigDecimal paymentAmount;

	@ApiModelProperty(value = "实例规格")
	private String instanceSpec;

	@ApiModelProperty(value = "计费项")
	private String billingItem;

	@ApiModelProperty(value = "地域")
	private String region;

	@ApiModelProperty(value = "单价单位")
	private String listPriceUnit;

	@ApiModelProperty(value = "资源组")
	private String resourceGroup;

	@ApiModelProperty(value = "产品Code")
	private String pipCode;

	@ApiModelProperty(value = "服务时长单位")
	private String servicePeriodUnit;

	@ApiModelProperty(value = "应付金额")
	private BigDecimal pretaxAmount;

	@ApiModelProperty(value = "商品Code")
	private String commodityCode;

	@ApiModelProperty(value = "产品名称", example = "赵六")
	private String productName;

	@ApiModelProperty(value = "实例昵称", example = "赵六")
	private String nickName;

	@ApiModelProperty(value = "产品明细")
	private String productDetail;

	@ApiModelProperty(value = "用量")
	private String aliUsage;

	@ApiModelProperty(value = "用量单位")
	private String usageUnit;

	@ApiModelProperty(value = "资源包抵扣")
	private String deductedByResourcePackage;

	@ApiModelProperty(value = "产品类型", example = "2")
	private String productType;

	@ApiModelProperty(value = "服务时长")
	private String servicePeriod;

	@ApiModelProperty(value = "可用区")
	private String zone;

	@ApiModelProperty(value = "单价")
	private String listPrice;

	@ApiModelProperty(value = "原始金额")
	private String pretaxGrossAmount;

	@ApiModelProperty(value = "产品代码")
	private String productCode;

	@ApiModelProperty(value = "计费方式", example = "1")
	private String billingType;

}
