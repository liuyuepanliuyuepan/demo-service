package cn.klmb.crm.module.member.service.instrument;

import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.framework.common.pojo.BiParams;
import cn.klmb.crm.module.member.controller.admin.instrument.vo.CrmCountRankVO;
import cn.klmb.crm.module.member.controller.admin.instrument.vo.CrmDataSummaryVO;
import cn.klmb.crm.module.member.controller.admin.instrument.vo.CrmInstrumentVO;
import java.util.List;

public interface CrmInstrumentService {


    /**
     * 获取数据
     *
     * @param biParams
     * @return CrmInstrumentVO
     */
    CrmInstrumentVO queryBulletin(BiParams biParams);


    /**
     * 新增排行榜(客户,联系人，跟进记录)
     *
     * @param biParams
     * @return
     */
    List<CrmCountRankVO> countRank(BiParams biParams);


    /**
     * 销售简报的详情
     *
     * @param biParams 参数
     * @return data
     */
    KlmbPage<?> queryBulletinInfo(BiParams biParams);


    /**
     * 数据汇总
     *
     * @param biParams
     * @return CrmInstrumentVO
     */
    CrmDataSummaryVO queryDataInfo(BiParams biParams);

}
