package cn.klmb.demo.module.system.service.sms;

import static cn.klmb.demo.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.klmb.demo.framework.sms.core.enums.SmsFrameworkErrorCodeConstants.SMS_CLIENT_NOT_EXIST;
import static cn.klmb.demo.module.system.enums.ErrorCodeConstants.SMS_CHANNEL_HAS_CHILDREN;
import static cn.klmb.demo.module.system.enums.ErrorCodeConstants.SMS_CHANNEL_NOT_EXISTS;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.klmb.demo.framework.base.core.service.KlmbBaseServiceImpl;
import cn.klmb.demo.framework.sms.core.client.SmsClient;
import cn.klmb.demo.framework.sms.core.client.SmsClientFactory;
import cn.klmb.demo.framework.sms.core.property.SmsChannelProperties;
import cn.klmb.demo.module.system.convert.sms.SysSmsChannelConvert;
import cn.klmb.demo.module.system.dao.sms.SysSmsChannelMapper;
import cn.klmb.demo.module.system.dto.sms.SysSmsChannelQueryDTO;
import cn.klmb.demo.module.system.entity.sms.SysSmsChannelDO;
import java.util.List;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;


/**
 * 系统管理-短信渠道
 *
 * @author liuyuepan
 * @date 2022/12/8
 */
@Service
public class SysSmsChannelServiceImpl extends
        KlmbBaseServiceImpl<SysSmsChannelDO, SysSmsChannelQueryDTO, SysSmsChannelMapper> implements
        SysSmsChannelService {

    private final SysSmsTemplateService sysSmsTemplateService;
    private final SmsClientFactory smsClientFactory;

    public SysSmsChannelServiceImpl(@Lazy SysSmsTemplateService sysSmsTemplateService,
            SmsClientFactory smsClientFactory, SysSmsChannelMapper mapper) {
        this.sysSmsTemplateService = sysSmsTemplateService;
        this.smsClientFactory = smsClientFactory;
        this.mapper = mapper;
    }

    @Override
    public boolean updateDO(SysSmsChannelDO sysSmsChannelDO) {
        // 校验存在
        this.validateSmsChannelExists(sysSmsChannelDO.getBizId());
        // 更新
        return super.updateDO(sysSmsChannelDO);
    }

    @Override
    public void removeByBizIds(List<String> bizIds) {
        if (CollUtil.isEmpty(bizIds)) {
            return;
        }
        bizIds.forEach(bizId -> {
            // 校验存在
            this.validateSmsChannelExists(bizId);
            // 校验是否有字典数据
            if (sysSmsTemplateService.countByChannelId(bizId) > 0) {
                throw exception(SMS_CHANNEL_HAS_CHILDREN);
            }
        });
        super.removeByBizIds(bizIds);
    }

    private void validateSmsChannelExists(String bizId) {
        if (mapper.selectByBizId(bizId) == null) {
            throw exception(SMS_CHANNEL_NOT_EXISTS);
        }
    }

    @Override
    public SmsClient getSmsClient(String bizId) {
        SysSmsChannelDO sysSmsChannelDO = mapper.selectByBizId(bizId);
        if (ObjectUtil.isEmpty(sysSmsChannelDO)) {
            throw exception(SMS_CHANNEL_NOT_EXISTS);
        }
        SmsChannelProperties channelProperties = SysSmsChannelConvert.INSTANCE.convert01(
                sysSmsChannelDO);
        SmsClient smsClient = smsClientFactory.createSmsClient(channelProperties);
        if (ObjectUtil.isNull(smsClient)) {
            throw exception(SMS_CLIENT_NOT_EXIST, bizId);
        }
        return smsClient;
    }
}