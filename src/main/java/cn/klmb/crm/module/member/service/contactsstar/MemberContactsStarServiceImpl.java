package cn.klmb.crm.module.member.service.contactsstar;

import cn.klmb.crm.framework.base.core.service.KlmbBaseServiceImpl;
import cn.klmb.crm.module.member.dao.contactsstar.MemberContactsStarMapper;
import cn.klmb.crm.module.member.dto.contactsstar.MemberContactsStarQueryDTO;
import cn.klmb.crm.module.member.entity.contactsstar.MemberContactsStarDO;
import org.springframework.stereotype.Service;


/**
 * 用户联系人标星关系 Service 实现类
 *
 * @author 超级管理员
 */
@Service
public class MemberContactsStarServiceImpl extends
        KlmbBaseServiceImpl<MemberContactsStarDO, MemberContactsStarQueryDTO, MemberContactsStarMapper> implements
        MemberContactsStarService {

    public MemberContactsStarServiceImpl(MemberContactsStarMapper mapper) {
        this.mapper = mapper;
    }

}
