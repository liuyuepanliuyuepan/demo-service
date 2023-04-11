package cn.klmb.crm.module.member.convert.userpool;


import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.module.member.controller.admin.userpool.vo.MemberUserPoolPageReqVO;
import cn.klmb.crm.module.member.controller.admin.userpool.vo.MemberUserPoolRespVO;
import cn.klmb.crm.module.member.controller.admin.userpool.vo.MemberUserPoolSaveReqVO;
import cn.klmb.crm.module.member.controller.admin.userpool.vo.MemberUserPoolUpdateReqVO;
import cn.klmb.crm.module.member.dto.userpool.MemberUserPoolQueryDTO;
import cn.klmb.crm.module.member.entity.userpool.MemberUserPoolDO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 公海 Convert
 *
 * @author 超级管理员
 */
@Mapper
public interface MemberUserPoolConvert {

    MemberUserPoolConvert INSTANCE = Mappers.getMapper(MemberUserPoolConvert.class);

    MemberUserPoolDO convert(MemberUserPoolSaveReqVO saveReqVO);

    MemberUserPoolDO convert(MemberUserPoolUpdateReqVO updateReqVO);

    KlmbPage<MemberUserPoolRespVO> convert(KlmbPage<MemberUserPoolDO> page);

    List<MemberUserPoolRespVO> convert(List<MemberUserPoolDO> list);

    MemberUserPoolRespVO convert(MemberUserPoolDO saveDO);

    MemberUserPoolQueryDTO convert(MemberUserPoolPageReqVO reqVO);

}
