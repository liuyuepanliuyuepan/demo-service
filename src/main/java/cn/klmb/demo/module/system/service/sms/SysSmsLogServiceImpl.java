package cn.klmb.demo.module.system.service.sms;

import cn.klmb.demo.framework.base.core.service.KlmbBaseServiceImpl;
import cn.klmb.demo.framework.common.pojo.CommonResult;
import cn.klmb.demo.module.system.dao.sms.SysSmsLogMapper;
import cn.klmb.demo.module.system.dto.sms.SysSmsLogQueryDTO;
import cn.klmb.demo.module.system.entity.sms.SysSmsLogDO;
import cn.klmb.demo.module.system.entity.sms.SysSmsTemplateDO;
import cn.klmb.demo.module.system.enums.sms.SysSmsReceiveStatusEnum;
import cn.klmb.demo.module.system.enums.sms.SysSmsSendStatusEnum;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import org.springframework.stereotype.Service;


/**
 * 系统管理-短信日志
 *
 * @author liuyuepan
 * @date 2022/12/8
 */
@Service
public class SysSmsLogServiceImpl extends
        KlmbBaseServiceImpl<SysSmsLogDO, SysSmsLogQueryDTO, SysSmsLogMapper> implements
        SysSmsLogService {

    public SysSmsLogServiceImpl(SysSmsLogMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public SysSmsLogDO createSmsLog(String mobile, String userId, Integer userType, Boolean isSend,
            SysSmsTemplateDO template, String templateContent, Map<String, Object> templateParams) {
        SysSmsLogDO smsLogDO = SysSmsLogDO.builder()
                // 根据是否要发送，设置状态
                .sendStatus(Objects.equals(isSend, true) ? SysSmsSendStatusEnum.INIT.getStatus()
                        : SysSmsSendStatusEnum.IGNORE.getStatus())
                // 设置手机相关字段
                .mobile(mobile)
                .userId(userId)
                .userType(userType)
                // 设置模板相关字段
                .templateId(template.getBizId())
                .templateCode(template.getCode())
                .templateType(template.getType())
                .templateContent(templateContent)
                .templateParams(templateParams)
                .apiTemplateId(template.getApiTemplateId())
                // 设置渠道相关字段
                .channelId(template.getChannelId())
                .channelCode(template.getChannelCode())
                // 设置接收相关字段
                .receiveStatus(SysSmsReceiveStatusEnum.INIT.getStatus())
                .build();
        return super.saveDO(smsLogDO) ? smsLogDO : null;
    }

    @Override
    public void updateSmsSendResult(String bizId, Integer sendCode, String sendMsg,
            String apiSendCode, String apiSendMsg, String apiRequestId, String apiSerialNo) {
        SysSmsSendStatusEnum sendStatus =
                CommonResult.isSuccess(sendCode) ? SysSmsSendStatusEnum.SUCCESS
                        : SysSmsSendStatusEnum.FAILURE;
        super.updateDO(SysSmsLogDO.builder()
                .bizId(bizId)
                .sendStatus(sendStatus.getStatus())
                .sendTime(LocalDateTime.now())
                .sendCode(sendCode)
                .sendMsg(sendMsg)
                .apiSendCode(apiSendCode)
                .apiSendMsg(apiSendMsg)
                .apiRequestId(apiRequestId)
                .apiSerialNo(apiSerialNo)
                .build());
    }

    @Override
    public void updateSmsReceiveResult(String bizId, Boolean success, LocalDateTime receiveTime,
            String apiReceiveCode, String apiReceiveMsg) {
        SysSmsSendStatusEnum receiveStatus =
                Objects.equals(success, true) ? SysSmsSendStatusEnum.SUCCESS
                        : SysSmsSendStatusEnum.FAILURE;
        super.updateDO(SysSmsLogDO.builder()
                .bizId(bizId)
                .receiveStatus(receiveStatus.getStatus())
                .receiveTime(receiveTime)
                .apiReceiveCode(apiReceiveCode)
                .apiReceiveMsg(apiReceiveMsg)
                .build());
    }
}