package cn.klmb.crm.module.member.dao.contacts;

import cn.klmb.crm.framework.base.core.dao.KlmbBaseMapper;
import cn.klmb.crm.module.member.dto.contacts.MemberContactsQueryDTO;
import cn.klmb.crm.module.member.entity.contacts.MemberContactsDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 联系人 Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface MemberContactsMapper extends
        KlmbBaseMapper<MemberContactsDO, MemberContactsQueryDTO> {

}
