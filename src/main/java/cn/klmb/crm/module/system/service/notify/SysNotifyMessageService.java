package cn.klmb.crm.module.system.service.notify;


import cn.klmb.crm.framework.base.core.service.KlmbBaseService;
import cn.klmb.crm.module.system.dto.notify.SysNotifyMessageQueryDTO;
import cn.klmb.crm.module.system.entity.notify.SysNotifyMessageDO;
import cn.klmb.crm.module.system.entity.notify.SysNotifyTemplateDO;
import java.util.Map;

/**
 * 站内信 Service 接口
 *
 * @author 超级管理员
 */
public interface SysNotifyMessageService extends
        KlmbBaseService<SysNotifyMessageDO, SysNotifyMessageQueryDTO> {


    /**
     * 创建站内信
     *
     * @param userId          用户编号
     * @param userType        用户类型
     * @param template        模版信息
     * @param templateContent 模版内容
     * @param templateParams  模版参数
     * @return 站内信编号
     */
    String createNotifyMessage(String userId, Integer userType,
            SysNotifyTemplateDO template, String templateContent,
            Map<String, Object> templateParams);


}
