package cn.klmb.crm.module.member.convert.teamactivity;

import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.module.member.controller.admin.teamactivity.vo.MemberTeamActivityPageReqVO;
import cn.klmb.crm.module.member.controller.admin.teamactivity.vo.MemberTeamActivityRespVO;
import cn.klmb.crm.module.member.controller.admin.teamactivity.vo.MemberTeamActivitySaveReqVO;
import cn.klmb.crm.module.member.controller.admin.teamactivity.vo.MemberTeamActivityUpdateReqVO;
import cn.klmb.crm.module.member.dto.teamactivity.MemberTeamActivityQueryDTO;
import cn.klmb.crm.module.member.entity.teamactivity.MemberTeamActivityDO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * crm团队成员活动 Convert
 *
 * @author 超级管理员
 */
@Mapper
public interface MemberTeamActivityConvert {

    MemberTeamActivityConvert INSTANCE = Mappers.getMapper(MemberTeamActivityConvert.class);

    MemberTeamActivityDO convert(MemberTeamActivitySaveReqVO saveReqVO);

    MemberTeamActivityDO convert(MemberTeamActivityUpdateReqVO updateReqVO);

    KlmbPage<MemberTeamActivityRespVO> convert(KlmbPage<MemberTeamActivityDO> page);

    List<MemberTeamActivityRespVO> convert(List<MemberTeamActivityDO> list);

    MemberTeamActivityRespVO convert(MemberTeamActivityDO saveDO);

    MemberTeamActivityQueryDTO convert(MemberTeamActivityPageReqVO reqVO);

}
