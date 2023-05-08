package cn.klmb.crm.module.member.service.instrument;

import cn.klmb.crm.framework.common.pojo.BiParams;
import cn.klmb.crm.module.member.controller.admin.instrument.vo.CrmInstrumentVO;

public interface CrmInstrumentService {


    /**
     * 获取数据
     *
     * @param biParams
     * @return CrmInstrumentVO
     */
    CrmInstrumentVO queryBulletin(BiParams biParams);

}
