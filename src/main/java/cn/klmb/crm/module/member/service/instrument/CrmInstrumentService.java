package cn.klmb.crm.module.member.service.instrument;

import cn.klmb.crm.framework.common.pojo.BiParams;
import cn.klmb.crm.module.member.controller.admin.instrument.vo.CrmInstrumentVO;

public interface CrmInstrumentService {


    /**
     * 获取数据
     *
     * @param dataType   1仅本人, 2 本人及下属,3进本部门,4本部门及下属部门
     * @param dateFilter 今天today,昨天yesterday,明天tomorrow,周week,上周lastWeek,下周nextWeek,本月month,上月lastMonth,下月nextMonth,本季度quarter,上一季度lastQuarter,下一季度nextQuarter,本年度year,上一年度lastYear,下一年度nextYear
     * @return
     */
    CrmInstrumentVO queryBulletin(BiParams biParams);

}
