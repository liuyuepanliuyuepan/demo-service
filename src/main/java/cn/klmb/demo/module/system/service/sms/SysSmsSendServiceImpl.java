package cn.klmb.demo.module.system.service.sms;

import static cn.klmb.demo.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.klmb.demo.module.system.enums.ErrorCodeConstants.SMS_SEND_MOBILE_NOT_EXISTS;
import static cn.klmb.demo.module.system.enums.ErrorCodeConstants.SMS_SEND_MOBILE_TEMPLATE_PARAM_MISS;
import static cn.klmb.demo.module.system.enums.ErrorCodeConstants.SMS_SEND_TEMPLATE_NOT_EXISTS;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.klmb.demo.framework.common.core.KeyValue;
import cn.klmb.demo.framework.common.enums.CommonStatusEnum;
import cn.klmb.demo.framework.common.enums.UserTypeEnum;
import cn.klmb.demo.framework.sms.core.client.SmsClient;
import cn.klmb.demo.framework.sms.core.client.SmsClientFactory;
import cn.klmb.demo.framework.sms.core.client.SmsCommonResult;
import cn.klmb.demo.framework.sms.core.client.dto.SmsReceiveRespDTO;
import cn.klmb.demo.framework.sms.core.client.dto.SmsSendRespDTO;
import cn.klmb.demo.module.system.entity.sms.SysSmsChannelDO;
import cn.klmb.demo.module.system.entity.sms.SysSmsLogDO;
import cn.klmb.demo.module.system.entity.sms.SysSmsTemplateDO;
import cn.klmb.demo.module.system.entity.user.SysUserDO;
import cn.klmb.demo.module.system.service.user.SysUserService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * 短信发送 Service 发送的实现
 *
 * @author 快乐萌宝
 */
@Service
public class SysSmsSendServiceImpl implements SysSmsSendService {

    private final SysUserService sysUserService;
    private final SysSmsChannelService sysSmsChannelService;
    private final SysSmsTemplateService sysSmsTemplateService;
    private final SysSmsLogService sysSmsLogService;
    private final SmsClientFactory smsClientFactory;

    public SysSmsSendServiceImpl(SysUserService sysUserService,
            SysSmsChannelService sysSmsChannelService, SysSmsTemplateService sysSmsTemplateService,
            SysSmsLogService sysSmsLogService, SmsClientFactory smsClientFactory) {
        this.sysUserService = sysUserService;
        this.sysSmsChannelService = sysSmsChannelService;
        this.sysSmsTemplateService = sysSmsTemplateService;
        this.sysSmsLogService = sysSmsLogService;
        this.smsClientFactory = smsClientFactory;
    }

    @Override
    public String sendSingleSmsToAdmin(String mobile, String userId, String templateCode,
            Map<String, Object> templateParams) {
        // 如果 mobile 为空，则加载用户编号对应的手机号
        if (StrUtil.isEmpty(mobile)) {
            SysUserDO user = sysUserService.getByBizId(userId);
            if (user != null) {
                mobile = user.getMobile();
            }
        }
        // 执行发送
        return this.sendSingleSms(mobile, userId, UserTypeEnum.ADMIN.getValue(), templateCode,
                templateParams);
    }

    @Override
    public String sendSingleSms(String mobile, String userId, Integer userType,
            String templateCode, Map<String, Object> templateParams) {
        // 校验短信模板是否合法
        SysSmsTemplateDO template = this.checkSmsTemplateValid(templateCode);
        // 校验短信渠道是否合法
        SysSmsChannelDO smsChannel = this.checkSmsChannelValid(template.getChannelId());

        // 校验手机号码是否存在
        mobile = this.checkMobile(mobile);
        // 构建有序的模板参数。为什么放在这个位置，是提前保证模板参数的正确性，而不是到了插入发送日志
        List<KeyValue<String, Object>> newTemplateParams = this.buildTemplateParams(template,
                templateParams);

        // 创建发送日志。如果模板被禁用，则不发送短信，只记录日志
        Boolean isSend = CommonStatusEnum.ENABLE.getStatus().equals(template.getStatus())
                && CommonStatusEnum.ENABLE.getStatus().equals(smsChannel.getStatus());
        String content = sysSmsTemplateService.formatContent(template.getContent(), templateParams);
        SysSmsLogDO sendLog = sysSmsLogService.createSmsLog(mobile, userId, userType, isSend,
                template, content, templateParams);

        // 发送 MQ 消息，异步执行发送短信
        if (isSend) {
            this.doSendSms(sendLog, newTemplateParams);
            // todo 发送短信
//            smsProducer.sendSmsSendMessage(sendLogId, mobile, template.getChannelId(),
//                    template.getApiTemplateId(), newTemplateParams);
        }
        return sendLog.getBizId();
    }

    public SysSmsChannelDO checkSmsChannelValid(String channelId) {
        // 获得短信模板。考虑到效率，从缓存中获取
        SysSmsChannelDO channelDO = sysSmsChannelService.getByBizId(channelId);
        // 短信模板不存在
        if (channelDO == null) {
            throw exception(SMS_SEND_TEMPLATE_NOT_EXISTS);
        }
        return channelDO;
    }

    public SysSmsTemplateDO checkSmsTemplateValid(String templateCode) {
        // 获得短信模板。考虑到效率，从缓存中获取
        SysSmsTemplateDO template = sysSmsTemplateService.getByCode(templateCode);
        // 短信模板不存在
        if (template == null) {
            throw exception(SMS_SEND_TEMPLATE_NOT_EXISTS);
        }
        return template;
    }

    /**
     * 将参数模板，处理成有序的 KeyValue 数组
     * <p>
     * 原因是，部分短信平台并不是使用 key 作为参数，而是数组下标，例如说腾讯云 <a
     * href="https://cloud.tencent.com/document/product/382/39023">API</a>
     *
     * @param template       短信模板
     * @param templateParams 原始参数
     * @return 处理后的参数
     */
    public List<KeyValue<String, Object>> buildTemplateParams(SysSmsTemplateDO template,
            Map<String, Object> templateParams) {
        return template.getParams().stream().map(key -> {
            Object value = templateParams.get(key);
            if (value == null) {
                throw exception(SMS_SEND_MOBILE_TEMPLATE_PARAM_MISS, key);
            }
            return new KeyValue<>(key, value);
        }).collect(Collectors.toList());
    }

    public String checkMobile(String mobile) {
        if (StrUtil.isEmpty(mobile)) {
            throw exception(SMS_SEND_MOBILE_NOT_EXISTS);
        }
        return mobile;
    }

    @Override
    public void doSendSms(SysSmsLogDO sendLog, List<KeyValue<String, Object>> newTemplateParams) {
        // 获得渠道对应的 SmsClient 客户端
        SmsClient smsClient = sysSmsChannelService.getSmsClient(sendLog.getChannelId());
        // 发送短信
        SmsCommonResult<SmsSendRespDTO> sendResult = smsClient.sendSms(sendLog.getBizId(),
                sendLog.getMobile(),
                sendLog.getApiTemplateId(), newTemplateParams);
        sysSmsLogService.updateSmsSendResult(sendLog.getBizId(), sendResult.getCode(),
                sendResult.getMsg(),
                sendResult.getApiCode(), sendResult.getApiMsg(), sendResult.getApiRequestId(),
                sendResult.getData() != null ? sendResult.getData().getSerialNo() : null);
    }

    @Override
    public void receiveSmsStatus(String channelCode, String text) throws Throwable {
        // 获得渠道对应的 SmsClient 客户端
        SmsClient smsClient = smsClientFactory.getSmsClient(channelCode);
        Assert.notNull(smsClient, "短信客户端({}) 不存在", channelCode);
        // 解析内容
        List<SmsReceiveRespDTO> receiveResults = smsClient.parseSmsReceiveStatus(text);
        if (CollUtil.isEmpty(receiveResults)) {
            return;
        }
        // 更新短信日志的接收结果. 因为量一般不大，所以先使用 for 循环更新
        receiveResults.forEach(result -> sysSmsLogService.updateSmsReceiveResult(result.getLogId(),
                result.getSuccess(), result.getReceiveTime(), result.getErrorCode(),
                result.getErrorMsg()));
    }

}
