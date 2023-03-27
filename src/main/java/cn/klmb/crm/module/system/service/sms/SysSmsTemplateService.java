package cn.klmb.crm.module.system.service.sms;

import cn.klmb.crm.framework.base.core.service.KlmbBaseService;
import cn.klmb.crm.module.system.dto.sms.SysSmsTemplateQueryDTO;
import cn.klmb.crm.module.system.entity.sms.SysSmsTemplateDO;
import java.util.List;
import java.util.Map;


/**
 * 系统管理-短信模板
 *
 * @author shilinchuan
 * @date 2022/12/8
 */
public interface SysSmsTemplateService extends
        KlmbBaseService<SysSmsTemplateDO, SysSmsTemplateQueryDTO> {

    /**
     * 格式化短信内容
     *
     * @param content 短信模板的内容
     * @param params  内容的参数
     * @return 格式化后的内容
     */
    String formatContent(String content, Map<String, Object> params);

    /**
     * 获得短信模板
     *
     * @param code 模板编码
     * @return 短信模板
     */
    SysSmsTemplateDO getByCode(String code);

    /**
     * 获得指定短信渠道下的短信模板数量
     *
     * @param channelId 短信渠道编号
     * @return 数量
     */
    Long countByChannelId(String channelId);

    /**
     * 获取参数
     *
     * @param content 内容
     * @return 参数列表
     */
    List<String> parseTemplateContentParams(String content);

}

