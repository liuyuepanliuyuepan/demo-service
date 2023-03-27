package cn.klmb.crm.module.system.service.sms;

import cn.klmb.crm.framework.base.core.service.KlmbBaseService;
import cn.klmb.crm.module.system.dto.sms.SysSmsLogQueryDTO;
import cn.klmb.crm.module.system.entity.sms.SysSmsLogDO;
import cn.klmb.crm.module.system.entity.sms.SysSmsTemplateDO;
import java.time.LocalDateTime;
import java.util.Map;


/**
 * 系统管理-短信日志
 *
 * @author shilinchuan
 * @date 2022/12/8
 */
public interface SysSmsLogService extends KlmbBaseService<SysSmsLogDO, SysSmsLogQueryDTO> {

    /**
     * 创建短信日志
     *
     * @param mobile          手机号
     * @param userId          用户编号
     * @param userType        用户类型
     * @param isSend          是否发送
     * @param template        短信模板
     * @param templateContent 短信内容
     * @param templateParams  短信参数
     * @return 发送日志编号
     */
    SysSmsLogDO createSmsLog(String mobile, String userId, Integer userType, Boolean isSend,
            SysSmsTemplateDO template, String templateContent, Map<String, Object> templateParams);

    /**
     * 更新日志的发送结果
     *
     * @param bizId        日志编号
     * @param sendCode     发送结果的编码
     * @param sendMsg      发送结果的提示
     * @param apiSendCode  短信 API 发送结果的编码
     * @param apiSendMsg   短信 API 发送失败的提示
     * @param apiRequestId 短信 API 发送返回的唯一请求 ID
     * @param apiSerialNo  短信 API 发送返回的序号
     */
    void updateSmsSendResult(String bizId, Integer sendCode, String sendMsg,
            String apiSendCode, String apiSendMsg, String apiRequestId, String apiSerialNo);

    /**
     * 更新日志的接收结果
     *
     * @param bizId          日志编号
     * @param success        是否接收成功
     * @param receiveTime    用户接收时间
     * @param apiReceiveCode API 接收结果的编码
     * @param apiReceiveMsg  API 接收结果的说明
     */
    void updateSmsReceiveResult(String bizId, Boolean success, LocalDateTime receiveTime,
            String apiReceiveCode, String apiReceiveMsg);

}

