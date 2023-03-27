package cn.klmb.crm.module.system.service.sms;

import static cn.klmb.crm.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.klmb.crm.module.system.enums.ErrorCodeConstants.SMS_CHANNEL_DISABLE;
import static cn.klmb.crm.module.system.enums.ErrorCodeConstants.SMS_CHANNEL_NOT_EXISTS;
import static cn.klmb.crm.module.system.enums.ErrorCodeConstants.SMS_TEMPLATE_CODE_DUPLICATE;
import static cn.klmb.crm.module.system.enums.ErrorCodeConstants.SMS_TEMPLATE_NOT_EXISTS;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.klmb.crm.framework.base.core.service.KlmbBaseServiceImpl;
import cn.klmb.crm.framework.common.enums.CommonStatusEnum;
import cn.klmb.crm.framework.sms.core.client.SmsClient;
import cn.klmb.crm.framework.sms.core.client.SmsCommonResult;
import cn.klmb.crm.framework.sms.core.client.dto.SmsTemplateRespDTO;
import cn.klmb.crm.module.system.dao.sms.SysSmsTemplateMapper;
import cn.klmb.crm.module.system.dto.sms.SysSmsTemplateQueryDTO;
import cn.klmb.crm.module.system.entity.sms.SysSmsChannelDO;
import cn.klmb.crm.module.system.entity.sms.SysSmsTemplateDO;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;
import org.springframework.stereotype.Service;


/**
 * 系统管理-短信模板
 *
 * @author shilinchuan
 * @date 2022/12/8
 */
@Service
public class SysSmsTemplateServiceImpl extends
        KlmbBaseServiceImpl<SysSmsTemplateDO, SysSmsTemplateQueryDTO, SysSmsTemplateMapper> implements
        SysSmsTemplateService {

    /**
     * 正则表达式，匹配 {} 中的变量
     */
    private static final Pattern PATTERN_PARAMS = Pattern.compile("\\{(.*?)}");

    private final SysSmsChannelService sysSmsChannelService;

    public SysSmsTemplateServiceImpl(SysSmsChannelService sysSmsChannelService,
            SysSmsTemplateMapper mapper) {
        this.sysSmsChannelService = sysSmsChannelService;
        this.mapper = mapper;
    }

    @Override
    public boolean saveDO(SysSmsTemplateDO sysSmsTemplateDO) {
        // 校验短信渠道
        checkSmsChannel(sysSmsTemplateDO.getChannelId());
        // 校验短信编码是否重复
        checkSmsTemplateCodeDuplicate(null, sysSmsTemplateDO.getCode());
        // 校验短信模板
        checkApiTemplate(sysSmsTemplateDO.getChannelId(), sysSmsTemplateDO.getApiTemplateId());
        return super.saveDO(sysSmsTemplateDO);
    }

    @Override
    public boolean updateDO(SysSmsTemplateDO sysSmsTemplateDO) {
        // 校验存在
        this.validateSmsTemplateExists(sysSmsTemplateDO.getBizId());
        // 校验短信渠道
        checkSmsChannel(sysSmsTemplateDO.getChannelId());
        // 校验短信编码是否重复
        checkSmsTemplateCodeDuplicate(sysSmsTemplateDO.getBizId(), sysSmsTemplateDO.getCode());
        // 校验短信模板
        checkApiTemplate(sysSmsTemplateDO.getChannelId(), sysSmsTemplateDO.getApiTemplateId());
        return super.updateDO(sysSmsTemplateDO);
    }

    @Override
    public void removeByBizIds(List<String> bizIds) {
        if (CollUtil.isEmpty(bizIds)) {
            return;
        }
        // 校验存在
        bizIds.forEach(this::validateSmsTemplateExists);
        super.removeByBizIds(bizIds);
    }

    @Override
    public String formatContent(String content, Map<String, Object> params) {
        return StrUtil.format(content, params);
    }

    @Override
    public SysSmsTemplateDO getByCode(String code) {
        return mapper.selectByCode(code);
    }

    @Override
    public Long countByChannelId(String channelId) {
        return mapper.selectCountByChannelId(channelId);
    }

    public List<String> parseTemplateContentParams(String content) {
        return ReUtil.findAllGroup1(PATTERN_PARAMS, content);
    }

    private SysSmsChannelDO checkSmsChannel(String channelId) {
        SysSmsChannelDO channelDO = sysSmsChannelService.getByBizId(channelId);
        if (channelDO == null) {
            throw exception(SMS_CHANNEL_NOT_EXISTS);
        }
        if (!Objects.equals(channelDO.getStatus(), CommonStatusEnum.ENABLE.getStatus())) {
            throw exception(SMS_CHANNEL_DISABLE);
        }
        return channelDO;
    }

    private void checkSmsTemplateCodeDuplicate(String id, String code) {
        SysSmsTemplateDO template = mapper.selectByCode(code);
        if (template == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的字典类型
        if (id == null) {
            throw exception(SMS_TEMPLATE_CODE_DUPLICATE, code);
        }
        if (ObjectUtil.notEqual(template.getBizId(), id)) {
            throw exception(SMS_TEMPLATE_CODE_DUPLICATE, code);
        }
    }

    private void checkApiTemplate(String channelId, String apiTemplateId) {
        // 获得短信模板
        SmsClient smsClient = sysSmsChannelService.getSmsClient(channelId);
        SmsCommonResult<SmsTemplateRespDTO> templateResult = smsClient.getSmsTemplate(
                apiTemplateId);
        // 校验短信模板是否正确
        templateResult.checkError();
    }

    private void validateSmsTemplateExists(String id) {
        if (mapper.selectByBizId(id) == null) {
            throw exception(SMS_TEMPLATE_NOT_EXISTS);
        }
    }
}