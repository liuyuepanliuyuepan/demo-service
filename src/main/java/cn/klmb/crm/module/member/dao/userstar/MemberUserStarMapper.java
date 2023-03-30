package cn.klmb.crm.module.member.dao.userstar;

import cn.klmb.crm.framework.base.core.dao.KlmbBaseMapper;
import cn.klmb.crm.module.member.dto.userstar.MemberUserStarQueryDTO;
import cn.klmb.crm.module.member.entity.userstar.MemberUserStarDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户客户标星关系 Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface MemberUserStarMapper extends KlmbBaseMapper<MemberUserStarDO, MemberUserStarQueryDTO> {

}
