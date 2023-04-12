package cn.klmb.crm.module.member.dao.instrument;

import cn.klmb.crm.framework.common.util.data.BiTimeUtil;
import cn.klmb.crm.module.member.controller.admin.instrument.vo.CrmInstrumentVO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CrmInstrumentMapper {

    /**
     * 查询销售简报
     *
     * @param biTimeEntity time
     * @param userIds      userIds
     * @return CrmInstrumentVO
     */
    CrmInstrumentVO queryBulletin(@Param("time") BiTimeUtil.BiTimeEntity biTimeEntity,
            @Param("userIds")
            List<String> userIds);


}
