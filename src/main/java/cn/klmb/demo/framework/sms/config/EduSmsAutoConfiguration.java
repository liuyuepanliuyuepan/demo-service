package cn.klmb.demo.framework.sms.config;

import cn.klmb.demo.framework.sms.core.client.SmsClientFactory;
import cn.klmb.demo.framework.sms.core.client.impl.SmsClientFactoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 短信配置类
 *
 * @author 快乐萌宝
 */
@Configuration
public class EduSmsAutoConfiguration {

    @Bean
    public SmsClientFactory smsClientFactory() {
        return new SmsClientFactoryImpl();
    }

}
