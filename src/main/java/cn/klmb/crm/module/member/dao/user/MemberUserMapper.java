package cn.klmb.crm.module.member.dao.user;

import cn.klmb.crm.framework.base.core.dao.KlmbBaseMapper;
import cn.klmb.crm.module.member.dto.user.MemberUserQueryDTO;
import cn.klmb.crm.module.member.entity.user.MemberUserDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 客户-用户 Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface MemberUserMapper extends KlmbBaseMapper<MemberUserDO, MemberUserQueryDTO> {

}
