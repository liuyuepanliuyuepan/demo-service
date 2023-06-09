package cn.klmb.crm.module.brd.controller.admin.aliyunbill.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import lombok.Data;
import lombok.ToString;

/**
 * 描述：
 *
 * @author: 蒋雷佳
 * @date: 2023/6/8 14:23
 */
@Data
@ToString
@ApiModel("账单明细")
public class BrdBillDetailRespVO {

	@ApiModelProperty(value = "账期")
	@JsonProperty("BillingCycle")
	private String billingCycle;

	@ApiModelProperty(value = "账单日期")
	@JsonProperty("BillingDate")
	private String billingDate;

	@ApiModelProperty(value = "账单类型：•	SubscriptionOrder （预付订单）。•	PayAsYouGoBill （后付账单）。•	Refund （退款）。•	Adjustment （调账） ")
	@JsonProperty("Item")
	private String item;

	@ApiModelProperty(value = "实例ID")
	@JsonProperty("InstanceID")
	private String instanceId;

	@ApiModelProperty(value = "币种，取值：•	CNY。•	USD。•	JPY。 ")
	@JsonProperty("Currency")
	private String currency;

	@ApiModelProperty(value = "订阅类型，取值：•	Subscription：预付费。•	PayAsYouGo：后付费。 ")
	@JsonProperty("SubscriptionType")
	private String subscriptionType;

	@ApiModelProperty(value = "实例详情配置")
	@JsonProperty("InstanceConfig")
	private String instanceConfig;

	@ApiModelProperty(value = "现金支付")
	@JsonProperty("PaymentAmount")
	private BigDecimal paymentAmount;

	@ApiModelProperty(value = "实例规格")
	@JsonProperty("InstanceSpec")
	private String instanceSpec;

	@ApiModelProperty(value = "计费项")
	@JsonProperty("BillingItem")
	private String billingItem;

	@ApiModelProperty(value = "地域")
	@JsonProperty("Region")
	private String region;

	@ApiModelProperty(value = "单价单位")
	@JsonProperty("ListPriceUnit")
	private String listPriceUnit;

	@ApiModelProperty(value = "资源组")
	@JsonProperty("ResourceGroup")
	private String resourceGroup;

	@ApiModelProperty(value = "产品Code")
	@JsonProperty("PipCode")
	private String pipCode;

	@ApiModelProperty(value = "服务时长单位")
	@JsonProperty("ServicePeriodUnit")
	private String servicePeriodUnit;

	@ApiModelProperty(value = "应付金额")
	@JsonProperty("PretaxAmount")
	private BigDecimal pretaxAmount;

	@ApiModelProperty(value = "商品Code")
	@JsonProperty("CommodityCode")
	private String commodityCode;

	@ApiModelProperty(value = "产品名称")
	@JsonProperty("ProductName")
	private String productName;

	@ApiModelProperty(value = "实例昵称")
	@JsonProperty("NickName")
	private String nickName;

	@ApiModelProperty(value = "产品明细")
	@JsonProperty("ProductDetail")
	private String productDetail;

	@ApiModelProperty(value = "用量")
	@JsonProperty("Usage")
	private String aliUsage;

	@ApiModelProperty(value = "用量单位")
	@JsonProperty("UsageUnit")
	private String usageUnit;

	@ApiModelProperty(value = "资源包抵扣")
	@JsonProperty("DeductedByResourcePackage")
	private String deductedByResourcePackage;

	@ApiModelProperty(value = "产品类型")
	@JsonProperty("ProductType")
	private String productType;

	@ApiModelProperty(value = "服务时长")
	@JsonProperty("ServicePeriod")
	private String servicePeriod;

	@ApiModelProperty(value = "可用区")
	@JsonProperty("Zone")
	private String zone;

	@ApiModelProperty(value = "单价")
	@JsonProperty("ListPrice")
	private String listPrice;

	@ApiModelProperty(value = "原始金额")
	@JsonProperty("PretaxGrossAmount")
	private String pretaxGrossAmount;

	@ApiModelProperty(value = "产品代码")
	@JsonProperty("ProductCode")
	private String productCode;

	@ApiModelProperty(value = "计费方式")
	@JsonProperty("BillingType")
	private String billingType;

}
