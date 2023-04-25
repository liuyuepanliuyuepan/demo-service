package cn.klmb.crm.module.business.service.detail;

import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.framework.base.core.pojo.KlmbScrollPage;
import cn.klmb.crm.framework.base.core.service.KlmbBaseService;
import cn.klmb.crm.module.business.controller.admin.detail.vo.BusinessDetailPageReqVO;
import cn.klmb.crm.module.business.controller.admin.detail.vo.BusinessDetailRespVO;
import cn.klmb.crm.module.business.controller.admin.detail.vo.BusinessDetailSaveReqVO;
import cn.klmb.crm.module.business.controller.admin.detail.vo.BusinessDetailScrollPageReqVO;
import cn.klmb.crm.module.business.controller.admin.detail.vo.BusinessDetailUpdateReqVO;
import cn.klmb.crm.module.business.controller.admin.detail.vo.CrmRelevanceBusinessBO;
import cn.klmb.crm.module.business.dto.detail.BusinessDetailQueryDTO;
import cn.klmb.crm.module.business.entity.detail.BusinessDetailDO;
import cn.klmb.crm.module.member.controller.admin.contacts.vo.MemberContactsPageReqVO;
import cn.klmb.crm.module.member.controller.admin.contacts.vo.MemberContactsRespVO;
import java.util.List;


/**
 * 商机 Service 接口
 *
 * @author 超级管理员
 */
public interface BusinessDetailService extends
        KlmbBaseService<BusinessDetailDO, BusinessDetailQueryDTO> {


    /**
     * 新建商机
     *
     * @param saveReqVO
     * @return
     */
    String saveBusiness(BusinessDetailSaveReqVO saveReqVO);


    /**
     * 根据业务id列表删除(逻辑删除)
     *
     * @param bizIds 业务id列表
     */
    void removeByBizIds(List<String> bizIds);


    /**
     * 更新商机
     *
     * @param updateReqVO
     * @return true/false
     */
    boolean updateBusiness(BusinessDetailUpdateReqVO updateReqVO);


    /**
     * 根据业务id查询商机信息
     *
     * @param bizId 业务id
     * @return 实体
     */
    BusinessDetailRespVO getBusinessByBizId(String bizId);

    /**
     * 分页列表
     *
     * @param reqVO 查询条件
     * @return 表单分页列表
     */
    KlmbPage<BusinessDetailRespVO> page(BusinessDetailPageReqVO reqVO);

    /**
     * 商机滚动分页
     *
     * @param reqVO
     * @return
     */
    KlmbScrollPage<BusinessDetailRespVO> pageScroll(BusinessDetailScrollPageReqVO reqVO);


    /**
     * 商机标星
     *
     * @param bizId
     */
    void star(String bizId);


    /**
     * 商机关联联系人
     *
     * @param relevanceBusinessBO 业务对象
     */
    void relateContacts(CrmRelevanceBusinessBO relevanceBusinessBO);


    /**
     * 商机解除+关联联系人
     *
     * @param relevanceBusinessBO 业务对象
     */
    void unrelateContacts(CrmRelevanceBusinessBO relevanceBusinessBO);


    /**
     * 分页列表
     *
     * @param reqVO 查询条件
     * @return 表单分页列表
     */
    KlmbPage<MemberContactsRespVO> pageContacts(MemberContactsPageReqVO reqVO);


    /**
     * 根据商机信息查询联系人列表
     *
     * @param reqVO 查询条件
     * @return 表单分页列表
     */
    List<MemberContactsRespVO> listContacts(MemberContactsPageReqVO reqVO);


}
