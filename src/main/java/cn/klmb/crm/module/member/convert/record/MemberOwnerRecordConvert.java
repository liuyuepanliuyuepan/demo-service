package cn.klmb.crm.module.member.convert.record;


import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.module.member.controller.admin.record.vo.MemberOwnerRecordPageReqVO;
import cn.klmb.crm.module.member.controller.admin.record.vo.MemberOwnerRecordRespVO;
import cn.klmb.crm.module.member.controller.admin.record.vo.MemberOwnerRecordSaveReqVO;
import cn.klmb.crm.module.member.controller.admin.record.vo.MemberOwnerRecordUpdateReqVO;
import cn.klmb.crm.module.member.dto.record.MemberOwnerRecordQueryDTO;
import cn.klmb.crm.module.member.entity.record.MemberOwnerRecordDO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 负责人变更记录 Convert
 *
 * @author 超级管理员
 */
@Mapper
public interface MemberOwnerRecordConvert {

    MemberOwnerRecordConvert INSTANCE = Mappers.getMapper(MemberOwnerRecordConvert.class);

    MemberOwnerRecordDO convert(MemberOwnerRecordSaveReqVO saveReqVO);

    MemberOwnerRecordDO convert(MemberOwnerRecordUpdateReqVO updateReqVO);

    KlmbPage<MemberOwnerRecordRespVO> convert(KlmbPage<MemberOwnerRecordDO> page);

    List<MemberOwnerRecordRespVO> convert(List<MemberOwnerRecordDO> list);

    MemberOwnerRecordRespVO convert(MemberOwnerRecordDO saveDO);

    MemberOwnerRecordQueryDTO convert(MemberOwnerRecordPageReqVO reqVO);

}
