package cn.klmb.crm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * 项目的启动类
 */
@SuppressWarnings("SpringComponentScan") // 忽略 IDEA 无法识别 ${crm.info.base-package}
@SpringBootApplication(scanBasePackages = {"${crm.info.base-package}"})
public class CrmApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrmApplication.class, args);
    }

}
