package cn.klmb.crm.framework.file.config;

import cn.klmb.crm.framework.file.core.client.FileClientFactory;
import cn.klmb.crm.framework.file.core.client.FileClientFactoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 文件配置类
 *
 * @author 快乐萌宝
 */
@Configuration
public class EduFileAutoConfiguration {

    @Bean
    public FileClientFactory fileClientFactory() {
        return new FileClientFactoryImpl();
    }

}
