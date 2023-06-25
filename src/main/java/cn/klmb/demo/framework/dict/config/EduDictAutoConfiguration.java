package cn.klmb.demo.framework.dict.config;

import cn.klmb.demo.framework.dict.core.util.DictFrameworkUtils;
import cn.klmb.demo.module.system.service.dict.SysDictDataService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EduDictAutoConfiguration {

    @Bean
    @SuppressWarnings("InstantiationOfUtilityClass")
    public DictFrameworkUtils dictUtils(SysDictDataService sysDictDataService) {
        DictFrameworkUtils.init(sysDictDataService);
        return new DictFrameworkUtils();
    }

}
