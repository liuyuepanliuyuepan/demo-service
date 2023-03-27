package cn.klmb.crm.module.system.convert.sms;

import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.module.system.controller.admin.sms.vo.template.SysSmsTemplatePageReqVO;
import cn.klmb.crm.module.system.controller.admin.sms.vo.template.SysSmsTemplateRespVO;
import cn.klmb.crm.module.system.controller.admin.sms.vo.template.SysSmsTemplateSaveReqVO;
import cn.klmb.crm.module.system.controller.admin.sms.vo.template.SysSmsTemplateUpdateReqVO;
import cn.klmb.crm.module.system.dto.sms.SysSmsTemplateQueryDTO;
import cn.klmb.crm.module.system.entity.sms.SysSmsTemplateDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 短信模板类型转换类
 *
 * @author shilinchuan
 * @date 2022/12/8
 */
@Mapper
public interface SysSmsTemplateConvert {

    SysSmsTemplateConvert INSTANCE = Mappers.getMapper(SysSmsTemplateConvert.class);

    SysSmsTemplateDO convert(SysSmsTemplateSaveReqVO saveReqVO);

    SysSmsTemplateDO convert(SysSmsTemplateUpdateReqVO updateReqVO);

    SysSmsTemplateRespVO convert(SysSmsTemplateDO sysDictTypeDO);

    SysSmsTemplateQueryDTO convert(SysSmsTemplatePageReqVO reqVO);

    KlmbPage<SysSmsTemplateRespVO> convert(KlmbPage<SysSmsTemplateDO> page);

}
