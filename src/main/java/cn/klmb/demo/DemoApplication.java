package cn.klmb.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 项目的启动类
 */
@SuppressWarnings("SpringComponentScan") // 忽略 IDEA 无法识别 ${demo.info.base-package}
@EnableScheduling
@EnableAsync
@SpringBootApplication(scanBasePackages = {"${demo.info.base-package}"})
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
