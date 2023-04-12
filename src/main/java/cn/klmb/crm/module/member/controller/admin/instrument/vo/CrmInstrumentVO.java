package cn.klmb.crm.module.member.controller.admin.instrument.vo;

import lombok.Data;

@Data
public class CrmInstrumentVO {

    /**
     * 客户数
     */
    private Long customerCount;

    /**
     * 联系人数
     */
    private Long contactsCount;

    /**
     * 跟进记录数
     */
    private Long activityCount;

}
