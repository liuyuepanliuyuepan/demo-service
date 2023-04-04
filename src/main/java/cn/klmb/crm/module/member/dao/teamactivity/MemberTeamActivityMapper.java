package cn.klmb.crm.module.member.dao.teamactivity;

import cn.klmb.crm.framework.base.core.dao.KlmbBaseMapper;
import cn.klmb.crm.module.member.dto.teamactivity.MemberTeamActivityQueryDTO;
import cn.klmb.crm.module.member.entity.teamactivity.MemberTeamActivityDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * crm团队成员活动 Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface MemberTeamActivityMapper extends
        KlmbBaseMapper<MemberTeamActivityDO, MemberTeamActivityQueryDTO> {

}
