package cn.klmb.demo.module.system.dao.sms;

import cn.klmb.demo.framework.base.core.dao.KlmbBaseMapper;
import cn.klmb.demo.module.system.dto.sms.SysSmsTemplateQueryDTO;
import cn.klmb.demo.module.system.entity.sms.SysSmsTemplateDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统管理-短信模板
 *
 * @author liuyuepan
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
