package cn.klmb.crm.module.member.convert.userpoolrelation;


import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.module.member.controller.admin.userpoolrelation.vo.MemberUserPoolRelationPageReqVO;
import cn.klmb.crm.module.member.controller.admin.userpoolrelation.vo.MemberUserPoolRelationRespVO;
import cn.klmb.crm.module.member.controller.admin.userpoolrelation.vo.MemberUserPoolRelationSaveReqVO;
import cn.klmb.crm.module.member.controller.admin.userpoolrelation.vo.MemberUserPoolRelationUpdateReqVO;
import cn.klmb.crm.module.member.dto.userpoolrelation.MemberUserPoolRelationQueryDTO;
import cn.klmb.crm.module.member.entity.userpoolrelation.MemberUserPoolRelationDO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 客户公海关联 Convert
 *
 * @author 超级管理员
 */
@Mapper
public interface MemberUserPoolRelationConvert {

    MemberUserPoolRelationConvert INSTANCE = Mappers.getMapper(MemberUserPoolRelationConvert.class);

    MemberUserPoolRelationDO convert(MemberUserPoolRelationSaveReqVO saveReqVO);

    MemberUserPoolRelationDO convert(MemberUserPoolRelationUpdateReqVO updateReqVO);

    KlmbPage<MemberUserPoolRelationRespVO> convert(KlmbPage<MemberUserPoolRelationDO> page);

    List<MemberUserPoolRelationRespVO> convert(List<MemberUserPoolRelationDO> list);

    MemberUserPoolRelationRespVO convert(MemberUserPoolRelationDO saveDO);

    MemberUserPoolRelationQueryDTO convert(MemberUserPoolRelationPageReqVO reqVO);

}
