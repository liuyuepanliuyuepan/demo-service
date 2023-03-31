package cn.klmb.crm.module.member.dao.contactsstar;

import cn.klmb.crm.framework.base.core.dao.KlmbBaseMapper;
import cn.klmb.crm.module.member.dto.contactsstar.MemberContactsStarQueryDTO;
import cn.klmb.crm.module.member.entity.contactsstar.MemberContactsStarDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户联系人标星关系 Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface MemberContactsStarMapper extends
        KlmbBaseMapper<MemberContactsStarDO, MemberContactsStarQueryDTO> {

}
