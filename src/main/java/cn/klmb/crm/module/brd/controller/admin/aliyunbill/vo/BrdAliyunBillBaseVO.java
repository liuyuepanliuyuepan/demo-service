package cn.klmb.crm.module.brd.controller.admin.aliyunbill.vo;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.math.BigDecimal;
import io.swagger.annotations.*;
import javax.validation.constraints.*;

/**
 * 博瑞迪阿里云账单 Base VO，提供给添加、修改、详细的子 VO 使用 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class BrdAliyunBillBaseVO {

	@ApiModelProperty(value = "账期")
	private String billingCycle;

	@ApiModelProperty(value = "账单日期")
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
