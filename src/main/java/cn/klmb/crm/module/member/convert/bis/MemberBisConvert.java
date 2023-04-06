package cn.klmb.crm.module.member.convert.bis;

import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.module.member.controller.admin.bis.vo.MemberBisPageReqVO;
import cn.klmb.crm.module.member.controller.admin.bis.vo.MemberBisRespVO;
import cn.klmb.crm.module.member.controller.admin.bis.vo.MemberBisSaveReqVO;
import cn.klmb.crm.module.member.controller.admin.bis.vo.MemberBisUpdateReqVO;
import cn.klmb.crm.module.member.dto.bis.MemberBisQueryDTO;
import cn.klmb.crm.module.member.entity.bis.MemberBisDO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 客户工商信息 Convert
 *
 * @author 超级管理员
 */
@Mapper
public interface MemberBisConvert {

    MemberBisConvert INSTANCE = Mappers.getMapper(MemberBisConvert.class);

    MemberBisDO convert(MemberBisSaveReqVO saveReqVO);

    MemberBisDO convert(MemberBisUpdateReqVO updateReqVO);

    KlmbPage<MemberBisRespVO> convert(KlmbPage<MemberBisDO> page);

    List<MemberBisRespVO> convert(List<MemberBisDO> list);

    MemberBisRespVO convert(MemberBisDO saveDO);

    MemberBisQueryDTO convert(MemberBisPageReqVO reqVO);

}
