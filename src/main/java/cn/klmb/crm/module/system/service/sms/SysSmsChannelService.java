package cn.klmb.crm.module.system.service.sms;

import cn.klmb.crm.framework.base.core.service.KlmbBaseService;
import cn.klmb.crm.framework.sms.core.client.SmsClient;
import cn.klmb.crm.module.system.dto.sms.SysSmsChannelQueryDTO;
import cn.klmb.crm.module.system.entity.sms.SysSmsChannelDO;


/**
 * 系统管理-短信渠道
 *
 * @author shilinchuan
 * @date 2022/12/8
 */
public interface SysSmsChannelService extends KlmbBaseService<SysSmsChannelDO, SysSmsChannelQueryDTO> {

    /**
     * 根据短信渠道业务ID获取短信客户端
     *
     * @param bizId 短信渠道业务id
     * @return 短信客户端
     */
    SmsClient getSmsClient(String bizId);

}

