package cn.klmb.crm.module.brd.dto.aliyunbill;

import cn.klmb.crm.framework.base.core.annotations.DtoFieldQuery;
import cn.klmb.crm.framework.base.core.annotations.DtoFieldQuery.Operator;
import cn.klmb.crm.framework.base.core.pojo.KlmbBaseQueryDTO;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * 博瑞迪阿里云账单 DO
 *
 * @author 超级管理员
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class BrdAliyunBillQueryDTO extends KlmbBaseQueryDTO {

	/**
	 * 账期
	 */
	@DtoFieldQuery
	private String billingCycle;
	/**
	 * 账单日期
	 */
	@DtoFieldQuery(queryType = Operator.BETWEEN)
	private String billingDate;
	/**
	 * 账单类型： •	SubscriptionOrder （预付订单）。 •	PayAsYouGoBill （后付账单）。 •	Refund （退款）。 •	Adjustment （调账）
	 */
	@DtoFieldQuery
	private String item;
	/**
	 * 实例ID
	 */
	@DtoFieldQuery
	private String instanceId;
	/**
	 * 币种，取值： •	CNY。 •	USD。 •	JPY。
	 */
	@DtoFieldQuery
	private String currency;
	/**
	 * 订阅类型，取值： •	Subscription：预付费。 •	PayAsYouGo：后付费。
	 */
	@DtoFieldQuery
	private String subscriptionType;
	/**
	 * 实例详情配置
	 */
	@DtoFieldQuery
	private String instanceConfig;
	/**
	 * 现金支付
	 */
	@DtoFieldQuery
	private BigDecimal paymentAmount;
	/**
	 * 实例规格
	 */
	@DtoFieldQuery
	private String instanceSpec;
	/**
	 * 计费项
	 */
	@DtoFieldQuery
	private String billingItem;
	/**
	 * 地域
	 */
	@DtoFieldQuery
	private String region;
	/**
	 * 单价单位
	 */
	@DtoFieldQuery
	private String listPriceUnit;
	/**
	 * 资源组
	 */
	@DtoFieldQuery
	private String resourceGroup;
	/**
	 * 产品Code
	 */
	@DtoFieldQuery
	private String pipCode;
	/**
	 * 服务时长单位
	 */
	@DtoFieldQuery
	private String servicePeriodUnit;
	/**
	 * 应付金额
	 */
	@DtoFieldQuery
	private BigDecimal pretaxAmount;
	/**
	 * 商品Code
	 */
	@DtoFieldQuery
	private String commodityCode;
	/**
	 * 产品名称
	 */
	@DtoFieldQuery(queryType = Operator.LIKE)
	private String productName;
	/**
	 * 实例昵称
	 */
	@DtoFieldQuery(queryType = Operator.LIKE)
	private String nickName;
	/**
	 * 产品明细
	 */
	@DtoFieldQuery
	private String productDetail;
	/**
	 * 用量
	 */
	@DtoFieldQuery
	private String aliUsage;
	/**
	 * 用量单位
	 */
	@DtoFieldQuery
	private String usageUnit;
	/**
	 * 资源包抵扣
	 */
	@DtoFieldQuery
	private String deductedByResourcePackage;
	/**
	 * 产品类型
	 */
	@DtoFieldQuery
	private String productType;
	/**
	 * 服务时长
	 */
	@DtoFieldQuery
	private String servicePeriod;
	/**
	 * 可用区
	 */
	@DtoFieldQuery
	private String zone;
	/**
	 * 单价
	 */
	@DtoFieldQuery
	private String listPrice;
	/**
	 * 原始金额
	 */
	@DtoFieldQuery
	private String pretaxGrossAmount;
	/**
	 * 产品代码
	 */
	@DtoFieldQuery
	private String productCode;
	/**
	 * 计费方式
	 */
	@DtoFieldQuery
	private String billingType;

}
