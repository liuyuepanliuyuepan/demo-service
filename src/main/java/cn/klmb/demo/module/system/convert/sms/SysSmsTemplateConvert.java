package cn.klmb.demo.module.system.convert.sms;

import cn.klmb.demo.framework.base.core.pojo.KlmbPage;
import cn.klmb.demo.module.system.controller.admin.sms.vo.template.SysSmsTemplatePageReqVO;
import cn.klmb.demo.module.system.controller.admin.sms.vo.template.SysSmsTemplateRespVO;
import cn.klmb.demo.module.system.controller.admin.sms.vo.template.SysSmsTemplateSaveReqVO;
import cn.klmb.demo.module.system.controller.admin.sms.vo.template.SysSmsTemplateUpdateReqVO;
import cn.klmb.demo.module.system.dto.sms.SysSmsTemplateQueryDTO;
import cn.klmb.demo.module.system.entity.sms.SysSmsTemplateDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 短信模板类型转换类
 *
 * @author liuyuepan
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
