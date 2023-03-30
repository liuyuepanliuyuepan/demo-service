package cn.klmb.crm.module.member.service.contacts;

import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.framework.base.core.service.KlmbBaseService;
import cn.klmb.crm.module.member.controller.admin.contacts.vo.MemberContactsPageReqVO;
import cn.klmb.crm.module.member.controller.admin.contacts.vo.MemberFirstContactsReqVO;
import cn.klmb.crm.module.member.dto.contacts.MemberContactsQueryDTO;
import cn.klmb.crm.module.member.entity.contacts.MemberContactsDO;


/**
 * 联系人 Service 接口
 *
 * @author 超级管理员
 */
public interface MemberContactsService extends
        KlmbBaseService<MemberContactsDO, MemberContactsQueryDTO> {


    /**
     * 分页列表
     *
     * @param reqVO 查询条件
     * @return 表单分页列表
     */
    KlmbPage<MemberContactsDO> page(MemberContactsPageReqVO reqVO);


    /**
     * 设置首要联系人
     *
     * @param reqVO
     */
    void setContacts(MemberFirstContactsReqVO reqVO);

}
