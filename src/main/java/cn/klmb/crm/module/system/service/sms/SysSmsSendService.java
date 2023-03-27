package cn.klmb.crm.module.system.service.sms;

import cn.klmb.crm.framework.common.core.KeyValue;
import cn.klmb.crm.module.system.entity.sms.SysSmsLogDO;
import java.util.List;
import java.util.Map;

/**
 * 短信发送 Service 接口
 *
 * @author 快乐萌宝
 */
public interface SysSmsSendService {

    /**
     * 发送单条短信给管理后台的用户
     * <p>
     * 在 mobile 为空时，使用 userId 加载对应管理员的手机号
     *
     * @param mobile         手机号
     * @param userId         用户编号
     * @param templateCode   短信模板编号
     * @param templateParams 短信模板参数
     * @return 发送日志编号
     */
    String sendSingleSmsToAdmin(String mobile, String userId, String templateCode,
            Map<String, Object> templateParams);

    /**
     * 发送单条短信给用户
     *
     * @param mobile         手机号
     * @param userId         用户编号
     * @param userType       用户类型
     * @param templateCode   短信模板编号
     * @param templateParams 短信模板参数
     * @return 发送日志编号
     */
    String sendSingleSms(String mobile, String userId, Integer userType,
            String templateCode, Map<String, Object> templateParams);

    default void sendBatchSms(List<String> mobiles, List<String> userIds, Integer userType,
            String templateCode, Map<String, Object> templateParams) {
        throw new UnsupportedOperationException("暂时不支持该操作，感兴趣可以实现该功能哟！");
    }

    /**
     * 执行真正的短信发送
     *
     * @param sysSmsLogDO       短信日志
     * @param newTemplateParams 短信模板参数
     */
    void doSendSms(SysSmsLogDO sysSmsLogDO, List<KeyValue<String, Object>> newTemplateParams);

    /**
     * 接收短信的接收结果
     *
     * @param channelCode 渠道编码
     * @param text        结果内容
     * @throws Throwable 处理失败时，抛出异常
     */
    void receiveSmsStatus(String channelCode, String text) throws Throwable;

}
