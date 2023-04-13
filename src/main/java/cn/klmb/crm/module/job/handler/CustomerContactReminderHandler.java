package cn.klmb.crm.module.job.handler;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.klmb.crm.framework.mq.message.WebSocketServer;
import cn.klmb.crm.module.member.entity.contacts.MemberContactsDO;
import cn.klmb.crm.module.member.entity.user.MemberUserDO;
import cn.klmb.crm.module.member.service.contacts.MemberContactsService;
import cn.klmb.crm.module.member.service.user.MemberUserService;
import cn.klmb.crm.module.system.entity.notify.SysNotifyMessageDO;
import cn.klmb.crm.module.system.enums.CrmEnum;
import cn.klmb.crm.module.system.manager.SysFeishuManager;
import cn.klmb.crm.module.system.service.notify.SysNotifyMessageService;
import cn.klmb.crm.module.system.service.notify.SysNotifySendService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 联系时间到期提醒
 *
 * @author liuyuepan
 */
@Component
@Slf4j
public class CustomerContactReminderHandler {

    private final WebSocketServer webSocketServer;

    private final SysNotifySendService sysNotifySendService;

    private final MemberUserService memberUserService;

    private final MemberContactsService memberContactsService;

    private final SysNotifyMessageService sysNotifyMessageService;

    private final SysFeishuManager sysFeishuManager;


    public CustomerContactReminderHandler(WebSocketServer webSocketServer,
            SysNotifySendService sysNotifySendService,
            MemberUserService memberUserService, MemberContactsService memberContactsService,
            SysNotifyMessageService sysNotifyMessageService, SysFeishuManager sysFeishuManager) {
        this.webSocketServer = webSocketServer;
        this.sysNotifySendService = sysNotifySendService;
        this.memberUserService = memberUserService;
        this.memberContactsService = memberContactsService;
        this.sysNotifyMessageService = sysNotifyMessageService;
        this.sysFeishuManager = sysFeishuManager;
    }

    @XxlJob("customerContactReminderHandler")
    public ReturnT<String> contactTimeExpiredReminderHandler() {
        log.info("进入[customerContactReminderHandler联系时间到期提醒接口]");
        XxlJobHelper.log("进入客户联系时间到期提醒接口");
        String jobParam = XxlJobHelper.getJobParam();
        List<String> split = StrUtil.split(jobParam, CharUtil.COMMA);
        if (CollUtil.isNotEmpty(split)) {
            String userId = split.get(0); // 团队成员id
            String contractType = split.get(1); // 将要联系用户的类型(2客户,3联系人)
            String contractBizId = split.get(2); // 将要联系用户的bizId
            String nextTime = split.get(3); // 联系时间
            Map<String, Object> map = new HashMap<>();
            if (StrUtil.equals(contractType, CrmEnum.CUSTOMER.getType().toString())) {
                MemberUserDO memberUserDO = memberUserService.getByBizId(contractBizId);
                if (ObjectUtil.isNotNull(memberUserDO)) {
                    map.put("name", memberUserDO.getName());
                    map.put("nextTime", nextTime);
                    map.put("contractType", CrmEnum.CUSTOMER.getRemarks());
                }
            }
            if (StrUtil.equals(contractType, CrmEnum.CONTACTS.getType().toString())) {
                MemberContactsDO memberContactsDO = memberContactsService.getByBizId(contractBizId);
                if (ObjectUtil.isNotNull(memberContactsDO)) {
                    map.put("name", memberContactsDO.getName());
                    map.put("nextTime", nextTime);
                    map.put("contractType", CrmEnum.CONTACTS.getRemarks());
                }
            }
            if (StrUtil.isNotBlank(map.get("name").toString())) {
                String bizId = sysNotifySendService.sendSingleNotifyToAdmin(userId,
                        "contactsRemind", map);
                SysNotifyMessageDO sysNotifyMessageDO = sysNotifyMessageService.getByBizId(
                        bizId);
                webSocketServer.sendOneMessage(userId,
                        JSONUtil.toJsonStr(JSONUtil.parse(sysNotifyMessageDO)));
                sysFeishuManager.sendMsg(StrUtil.format("CRM-客户【{}】下次联系时间【{}】", map.get("name"),
                        map.get("nextTime")));

                // 任务执行结束后销毁
                //  xxlJobApiUtils.deleteTask(XxlJobHelper.getJobId());
            }
        }
        return ReturnT.SUCCESS;
    }

}

