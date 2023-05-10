package cn.klmb.crm.module.member.dao.instrument;

import cn.klmb.crm.framework.common.util.data.BiTimeUtil;
import cn.klmb.crm.module.member.controller.admin.instrument.vo.CrmCountRankVO;
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


    List<CrmCountRankVO> customerCountRank(
            @Param("time") BiTimeUtil.BiTimeEntity biTimeEntity,
            @Param("userIds")
            List<String> userIds);

    List<CrmCountRankVO> contactsCountRank(
            @Param("time") BiTimeUtil.BiTimeEntity biTimeEntity,
            @Param("userIds")
            List<String> userIds);


    List<CrmCountRankVO> recordCountRank(
            @Param("time") BiTimeUtil.BiTimeEntity biTimeEntity,
            @Param("userIds")
            List<String> userIds);


    /**
     * 按条件查询新增客户
     *
     * @param biTimeEntity
     * @param userIds
     * @return
     */
    List<String> newCustomer(@Param("time") BiTimeUtil.BiTimeEntity biTimeEntity,
            @Param("userIds")
            List<String> userIds);

    /**
     * 按条件查询新增联系人
     *
     * @param biTimeEntity
     * @param userIds
     * @return
     */
    List<String> newContacts(@Param("time") BiTimeUtil.BiTimeEntity biTimeEntity,
            @Param("userIds")
            List<String> userIds);

    /**
     * 按条件查询新增商机
     *
     * @param biTimeEntity
     * @param userIds
     * @return
     */
    List<String> newBusiness(@Param("time") BiTimeUtil.BiTimeEntity biTimeEntity,
            @Param("userIds")
            List<String> userIds);


    /**
     * 按条件查询新增跟进记录
     *
     * @param biTimeEntity
     * @param userIds
     * @return
     */
    List<String> newActivity(@Param("time") BiTimeUtil.BiTimeEntity biTimeEntity,
            @Param("userIds")
            List<String> userIds);

    //新增联系人详情

    //新增跟进记录详情

    //新增


}
