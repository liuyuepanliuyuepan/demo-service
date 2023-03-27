package cn.klmb.crm.module.system.dao.sms;

import cn.klmb.crm.framework.base.core.dao.KlmbBaseMapper;
import cn.klmb.crm.module.system.dto.sms.SysSmsTemplateQueryDTO;
import cn.klmb.crm.module.system.entity.sms.SysSmsTemplateDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统管理-短信模板
 *
 * @author shilinchuan
 * @date 2022/12/8
 */
@Mapper
public interface SysSmsTemplateMapper extends KlmbBaseMapper<SysSmsTemplateDO, SysSmsTemplateQueryDTO> {

    default SysSmsTemplateDO selectByCode(String code) {
        return selectOne(SysSmsTemplateDO::getCode, code);
    }

    default Long selectCountByChannelId(String channelId) {
        return selectCount(SysSmsTemplateDO::getChannelId, channelId);
    }

}
