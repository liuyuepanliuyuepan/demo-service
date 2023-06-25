package cn.klmb.demo.framework.sms.core.client;


import cn.klmb.demo.framework.sms.core.client.impl.AbstractSmsClient;
import cn.klmb.demo.framework.sms.core.property.SmsChannelProperties;

/**
 * 短信客户端的工厂接口
 *
 * @author 快乐萌宝
 * @since 2021/1/28 14:01
 */
public interface SmsClientFactory {

    /**
     * 获得短信 Client
     *
     * @param channelId 渠道编号
     * @return 短信 Client
     */
    SmsClient getSmsClient(Long channelId);

    /**
     * 获得短信 Client
     *
     * @param channelCode 渠道编码
     * @return 短信 Client
     */
    SmsClient getSmsClient(String channelCode);

    /**
     * 创建短信 Client
     *
     * @param properties 配置对象
     */
    void createOrUpdateSmsClient(SmsChannelProperties properties);

    /**
     * 新建短信客户端
     *
     * @param properties 配置信息
     * @return 短信客户端
     */
    AbstractSmsClient createSmsClient(SmsChannelProperties properties);

}
