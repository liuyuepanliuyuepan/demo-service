package cn.klmb.crm.module.system.dao.notify;


import cn.klmb.crm.framework.base.core.dao.KlmbBaseMapper;
import cn.klmb.crm.module.system.dto.notify.SysNotifyTemplateQueryDTO;
import cn.klmb.crm.module.system.entity.notify.SysNotifyTemplateDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 站内信模板 Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface SysNotifyTemplateMapper extends
        KlmbBaseMapper<SysNotifyTemplateDO, SysNotifyTemplateQueryDTO> {

}
