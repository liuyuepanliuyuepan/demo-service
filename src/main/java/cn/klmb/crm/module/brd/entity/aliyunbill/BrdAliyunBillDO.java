package cn.klmb.crm.module.brd.entity.aliyunbill;

import cn.klmb.crm.framework.base.core.entity.KlmbBaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName(value = "brd_aliyun_bill", autoResultMap = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class BrdAliyunBillDO extends KlmbBaseDO {

	/**
	 * 账期
	 */
	private String billingCycle;
	/**
	 * 账单日期
	 */
	private String billingDate;
	/**
	 * 账单类型： •	SubscriptionOrder （预付订单）。 •	PayAsYouGoBill （后付账单）。 •	Refund （退款）。 •	Adjustment （调账）
	 */
	private String item;
	/**
	 * 实例ID
	 */
	private String instanceId;
	/**
	 * 币种，取值： •	CNY。 •	USD。 •	JPY。
	 */
	private String currency;
	/**
	 * 订阅类型，取值： •	Subscription：预付费。 •	PayAsYouGo：后付费。
	 */
	private String subscriptionType;
	/**
	 * 实例详情配置
	 */
	private String instanceConfig;
	/**
	 * 现金支付
	 */
	private BigDecimal paymentAmount;
	/**
	 * 实例规格
	 */
	private String instanceSpec;
	/**
	 * 计费项
	 */
	private String billingItem;
	/**
	 * 地域
	 */
	private String region;
	/**
	 * 单价单位
	 */
	private String listPriceUnit;
	/**
	 * 资源组
	 */
	private String resourceGroup;
	/**
	 * 产品Code
	 */
	private String pipCode;
	/**
	 * 服务时长单位
	 */
	private String servicePeriodUnit;
	/**
	 * 应付金额
	 */
	private BigDecimal pretaxAmount;
	/**
	 * 商品Code
	 */
	private String commodityCode;
	/**
	 * 产品名称
	 */
	private String productName;
	/**
	 * 实例昵称
	 */
	private String nickName;
	/**
	 * 产品明细
	 */
	private String productDetail;
	/**
	 * 用量
	 */
	private String aliUsage;
	/**
	 * 用量单位
	 */
	private String usageUnit;
	/**
	 * 资源包抵扣
	 */
	private String deductedByResourcePackage;
	/**
	 * 产品类型
	 */
	private String productType;
	/**
	 * 服务时长
	 */
	private String servicePeriod;
	/**
	 * 可用区
	 */
	private String zone;
	/**
	 * 单价
	 */
	private String listPrice;
	/**
	 * 原始金额
	 */
	private String pretaxGrossAmount;
	/**
	 * 产品代码
	 */
	private String productCode;
	/**
	 * 计费方式
	 */
	private String billingType;

}
