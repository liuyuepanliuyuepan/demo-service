package cn.klmb.demo.module.system.convert.notify;


import cn.klmb.demo.framework.base.core.pojo.KlmbPage;
import cn.klmb.demo.module.system.controller.admin.notify.vo.template.SysNotifyTemplatePageReqVO;
import cn.klmb.demo.module.system.controller.admin.notify.vo.template.SysNotifyTemplateRespVO;
import cn.klmb.demo.module.system.controller.admin.notify.vo.template.SysNotifyTemplateSaveReqVO;
import cn.klmb.demo.module.system.controller.admin.notify.vo.template.SysNotifyTemplateUpdateReqVO;
import cn.klmb.demo.module.system.dto.notify.SysNotifyTemplateQueryDTO;
import cn.klmb.demo.module.system.entity.notify.SysNotifyTemplateDO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 站内信模板 Convert
 *
 * @author 超级管理员
 */
@Mapper
public interface SysNotifyTemplateConvert {

    SysNotifyTemplateConvert INSTANCE = Mappers.getMapper(SysNotifyTemplateConvert.class);

    SysNotifyTemplateDO convert(SysNotifyTemplateSaveReqVO saveReqVO);

    SysNotifyTemplateDO convert(SysNotifyTemplateUpdateReqVO updateReqVO);

    KlmbPage<SysNotifyTemplateRespVO> convert(KlmbPage<SysNotifyTemplateDO> page);

    List<SysNotifyTemplateRespVO> convert(List<SysNotifyTemplateDO> list);

    SysNotifyTemplateRespVO convert(SysNotifyTemplateDO saveDO);

    SysNotifyTemplateQueryDTO convert(SysNotifyTemplatePageReqVO reqVO);

}
