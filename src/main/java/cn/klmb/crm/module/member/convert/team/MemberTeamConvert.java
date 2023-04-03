package cn.klmb.crm.module.member.convert.team;

import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.module.member.controller.admin.team.vo.MemberTeamPageReqVO;
import cn.klmb.crm.module.member.controller.admin.team.vo.MemberTeamRespVO;
import cn.klmb.crm.module.member.controller.admin.team.vo.MemberTeamSaveReqVO;
import cn.klmb.crm.module.member.controller.admin.team.vo.MemberTeamUpdateReqVO;
import cn.klmb.crm.module.member.dto.team.MemberTeamQueryDTO;
import cn.klmb.crm.module.member.entity.team.MemberTeamDO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * crm团队成员 Convert
 *
 * @author 超级管理员
 */
@Mapper
public interface MemberTeamConvert {

    MemberTeamConvert INSTANCE = Mappers.getMapper(MemberTeamConvert.class);

    MemberTeamDO convert(MemberTeamSaveReqVO saveReqVO);

    MemberTeamDO convert(MemberTeamUpdateReqVO updateReqVO);

    KlmbPage<MemberTeamRespVO> convert(KlmbPage<MemberTeamDO> page);

    List<MemberTeamRespVO> convert(List<MemberTeamDO> list);

    MemberTeamRespVO convert(MemberTeamDO saveDO);

    MemberTeamQueryDTO convert(MemberTeamPageReqVO reqVO);

}
