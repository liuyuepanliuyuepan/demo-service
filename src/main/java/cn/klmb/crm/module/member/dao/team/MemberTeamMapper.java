package cn.klmb.crm.module.member.dao.team;

import cn.klmb.crm.framework.base.core.dao.KlmbBaseMapper;
import cn.klmb.crm.module.member.dto.team.MemberTeamQueryDTO;
import cn.klmb.crm.module.member.entity.team.MemberTeamDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * crm团队成员 Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface MemberTeamMapper extends KlmbBaseMapper<MemberTeamDO, MemberTeamQueryDTO> {

}
