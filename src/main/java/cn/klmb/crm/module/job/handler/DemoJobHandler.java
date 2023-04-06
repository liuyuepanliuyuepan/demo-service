package cn.klmb.crm.module.job.handler;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author liuyuepan
 */
@Component
@Slf4j
public class DemoJobHandler {

    @XxlJob("demoJobHandler")
    public ReturnT<String> userJobHandler(String param) {
        String jobParam = XxlJobHelper.getJobParam();
        XxlJobHelper.log(jobParam);
        System.out.println("hello, user..." + jobParam);
        int i = 1 / 0;
        return ReturnT.SUCCESS;
    }
}
