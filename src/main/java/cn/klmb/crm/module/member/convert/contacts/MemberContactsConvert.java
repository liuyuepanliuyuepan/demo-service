package cn.klmb.crm.module.member.convert.contacts;

import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.module.member.controller.admin.contacts.vo.MemberContactsPageReqVO;
import cn.klmb.crm.module.member.controller.admin.contacts.vo.MemberContactsRespVO;
import cn.klmb.crm.module.member.controller.admin.contacts.vo.MemberContactsSaveReqVO;
import cn.klmb.crm.module.member.controller.admin.contacts.vo.MemberContactsUpdateReqVO;
import cn.klmb.crm.module.member.dto.contacts.MemberContactsQueryDTO;
import cn.klmb.crm.module.member.entity.contacts.MemberContactsDO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 联系人 Convert
 *
 * @author 超级管理员
 */
@Mapper
public interface MemberContactsConvert {

    MemberContactsConvert INSTANCE = Mappers.getMapper(MemberContactsConvert.class);

    MemberContactsDO convert(MemberContactsSaveReqVO saveReqVO);

    MemberContactsDO convert(MemberContactsUpdateReqVO updateReqVO);

    KlmbPage<MemberContactsRespVO> convert(KlmbPage<MemberContactsDO> page);

    List<MemberContactsRespVO> convert(List<MemberContactsDO> list);

    MemberContactsRespVO convert(MemberContactsDO saveDO);

    MemberContactsQueryDTO convert(MemberContactsPageReqVO reqVO);

}
