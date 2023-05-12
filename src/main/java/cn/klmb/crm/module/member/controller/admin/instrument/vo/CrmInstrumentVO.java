package cn.klmb.crm.module.member.controller.admin.instrument.vo;

import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CrmInstrumentVO {

    /**
     * 客户数
     */
    @ApiModelProperty(value = "客户数")
    private Long customerCount;

    /**
     * 联系人数
     */
    @ApiModelProperty(value = "联系人数")
    private Long contactsCount;

    /**
     * 跟进记录数(客户+商机)
     */
    @ApiModelProperty(value = "跟进记录数(客户+商机)")
    private Long activityCount;

    /**
     * 新增商机数
     */
    @ApiModelProperty(value = "新增商机数")
    private Long businessCount;


    /**
     * 商机金额
     */
    @ApiModelProperty(value = "商机金额")
    private BigDecimal businessMoney;


}
