package cn.klmb.crm.module.member.convert.user;

import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.framework.base.core.pojo.KlmbScrollPage;
import cn.klmb.crm.module.member.controller.admin.user.vo.MemberUserPageReqVO;
import cn.klmb.crm.module.member.controller.admin.user.vo.MemberUserRespVO;
import cn.klmb.crm.module.member.controller.admin.user.vo.MemberUserSaveReqVO;
import cn.klmb.crm.module.member.controller.admin.user.vo.MemberUserScrollPageReqVO;
import cn.klmb.crm.module.member.controller.admin.user.vo.MemberUserSimpleRespVO;
import cn.klmb.crm.module.member.controller.admin.user.vo.MemberUserUpdateReqVO;
import cn.klmb.crm.module.member.dto.user.MemberUserQueryDTO;
import cn.klmb.crm.module.member.entity.user.MemberUserDO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 客户-用户 Convert
 *
 * @author 超级管理员
 */
@Mapper
public interface MemberUserConvert {

    MemberUserConvert INSTANCE = Mappers.getMapper(MemberUserConvert.class);

    MemberUserDO convert(MemberUserSaveReqVO saveReqVO);

    MemberUserDO convert(MemberUserUpdateReqVO updateReqVO);

    KlmbPage<MemberUserRespVO> convert(KlmbPage<MemberUserDO> page);

    List<MemberUserRespVO> convert(List<MemberUserDO> list);

    MemberUserRespVO convert(MemberUserDO saveDO);

    MemberUserQueryDTO convert(MemberUserPageReqVO reqVO);

    MemberUserQueryDTO convert(MemberUserScrollPageReqVO bean);

    List<MemberUserSimpleRespVO> convert01(List<MemberUserDO> beans);

    KlmbScrollPage<MemberUserRespVO> convert(KlmbScrollPage<MemberUserDO> page);

}
