package cn.klmb.crm.framework.job.util;

import static cn.klmb.crm.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.klmb.crm.module.system.enums.ErrorCodeConstants.XXL_JOB_INTERFACE_CALL_ERROR;
import static cn.klmb.crm.module.system.enums.ErrorCodeConstants.XXL_JOB_NOT_GAIN_GROUP_ID;

import cn.klmb.crm.framework.job.config.XxlJobProperties;
import cn.klmb.crm.framework.job.entity.XxlJobInfo;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.util.XxlJobRemotingUtil;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;

//@Component
public class XxlJobUtil {

    public static final int SUCCESS_CODE = 200;
    private static final String ADD_URL = "/jobinfo/add";
    private static final String UPDATE_URL = "/jobinfo/update";
    private static final String REMOVE_URL = "/jobinfo/remove";
    private static final String PAUSE_URL = "/jobinfo/stop";
    private static final String START_URL = "/jobinfo/start";
    private static final String ADD_AND_START_URL = "/jobinfo/add-and-start";
    private static final String GET_GROUP_ID = "/jobgroup/get-group-id";

    private final XxlJobProperties xxlJobProperties;

    public XxlJobUtil(XxlJobProperties xxlJobProperties) {
        this.xxlJobProperties = xxlJobProperties;
    }

    /**
     * 添加任务
     *
     * @param jobInfo 任务信息
     * @return {@link String} 任务id
     * @author kritofgo
     * @date 2022/05/27 17:37
     **/
    public String add(XxlJobInfo jobInfo) {
        jobInfo.setJobGroup(xxlJobProperties.getGroupId());
        return doPost(xxlJobProperties.getAdminAddresses() + ADD_URL, jobInfo, String.class);
    }

    /**
     * 初始化获取执行器id
     *
     * @author kritofgo
     * @date 2022/05/27 17:37
     **/
    public void initGroupId() {
        // 查询对应groupId:
        Map<String, Object> param = new HashMap<>(4);
        param.put("appname", xxlJobProperties.getAppname());
        Integer groupId = doPost(xxlJobProperties.getAdminAddresses() + GET_GROUP_ID, param,
                Integer.class);
        if (groupId == null) {
            throw exception(XXL_JOB_NOT_GAIN_GROUP_ID, xxlJobProperties.getAppname());
        }
        xxlJobProperties.setGroupId(groupId);
    }

    /**
     * 修改执行时间
     *
     * @param id   任务id
     * @param cron cron表达式
     * @return {@link String}
     * @author kritofgo
     * @date 2022/05/27 17:38
     **/
    public String update(int id, String cron) {
        Map<String, Object> param = new HashMap<>(4);
        param.put("id", id);
        param.put("jobCron", cron);
        return doPost(xxlJobProperties.getAdminAddresses() + UPDATE_URL, param, String.class);
    }

    /**
     * 删除任务
     *
     * @param id 任务id
     * @return {@link String}
     * @author kritofgo
     * @date 2022/05/27 17:39
     **/
    public String remove(int id) {
        Map<String, Object> param = new HashMap<>(4);
        param.put("id", id);
        return doPost(xxlJobProperties.getAdminAddresses() + REMOVE_URL, param, String.class);
    }

    /**
     * 暂停任务
     *
     * @param id 任务id
     * @return {@link String}
     * @author kritofgo
     * @date 2022/05/27 17:40
     **/
    public String pause(int id) {
        Map<String, Object> param = new HashMap<>(4);
        param.put("id", id);
        return doPost(xxlJobProperties.getAdminAddresses() + PAUSE_URL, param, String.class);
    }

    /**
     * 开始任务
     *
     * @param id 任务id
     * @return {@link String}
     * @author kritofgo
     * @date 2022/05/27 17:40
     **/
    public String start(int id) {
        Map<String, Object> param = new HashMap<>();
        param.put("id", id);
        return doPost(xxlJobProperties.getAdminAddresses() + START_URL, param, String.class);
    }

    /**
     * 添加并启动
     *
     * @param jobInfo 任务信息
     * @return {@link String}
     * @author kritofgo
     * @date 2022/05/27 20:10
     **/
    public String addAndStart(XxlJobInfo jobInfo) {
        jobInfo.setJobGroup(xxlJobProperties.getGroupId());
        return doPost(xxlJobProperties.getAdminAddresses() + ADD_AND_START_URL, jobInfo,
                String.class);
    }

    @SuppressWarnings("unchecked")
    public <T> T doPost(String url, Object json, Class<T> clazz) {
        ReturnT<T> returnT = XxlJobRemotingUtil.postBody(url, xxlJobProperties.getAccessToken(), 3,
                json, clazz);
        if (returnT.getCode() != SUCCESS_CODE) {
            throw exception(XXL_JOB_INTERFACE_CALL_ERROR,
                    returnT.getCode(), returnT.getMsg());
        }
        return returnT.getContent();
    }

    @PostConstruct
    public void init() {
        initGroupId();
    }

}

