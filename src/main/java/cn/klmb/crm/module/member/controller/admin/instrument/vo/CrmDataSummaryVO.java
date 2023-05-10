package cn.klmb.crm.module.member.controller.admin.instrument.vo;

import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 数据汇总详情
 */
@Data
public class CrmDataSummaryVO {

    /**
     * 新增客户数
     */
    @ApiModelProperty(value = "新增客户数")
    private Long allCustomer;

    /**
     * 转成交客户
     */
    @ApiModelProperty(value = "转成交客户")
    private Long dealCustomer;


    /**
     * 放入公海客户
     */
    @ApiModelProperty(value = "放入公海客户")
    private Long putInPoolNum;

    /**
     * 公海池领取
     */
    @ApiModelProperty(value = "公海池领取")
    private Long receiveNum;

    /**
     * 新增商机
     */
    @ApiModelProperty(value = "新增商机")
    private Long allBusiness;


    /**
     * 赢单商机
     */
    @ApiModelProperty(value = "赢单商机")
    private Long endBusiness;

    /**
     * 输单商机
     */
    @ApiModelProperty(value = "输单商机")
    private Long loseBusiness;


    /**
     * 商机总金额
     */
    @ApiModelProperty(value = "商机总金额")
    private BigDecimal businessMoney;


    /**
     * 跟进客户数
     */
    @ApiModelProperty(value = "跟进客户数")
    private Long activityNum;

    /**
     * 新增未跟进客户数
     */
    @ApiModelProperty(value = "新增未跟进客户数")
    private Long activityRealNum;


}
