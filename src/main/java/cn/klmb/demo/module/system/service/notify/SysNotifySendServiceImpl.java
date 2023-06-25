package cn.klmb.demo.module.system.service.notify;


import static cn.klmb.demo.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.klmb.demo.module.system.enums.ErrorCodeConstants.NOTICE_NOT_FOUND;
import static cn.klmb.demo.module.system.enums.ErrorCodeConstants.NOTIFY_SEND_TEMPLATE_PARAM_MISS;

import cn.klmb.demo.framework.common.enums.CommonStatusEnum;
import cn.klmb.demo.framework.common.enums.UserTypeEnum;
import cn.klmb.demo.module.system.entity.notify.SysNotifyTemplateDO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.annotations.VisibleForTesting;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * 站内信发送 Service 实现类
 *
 * @author xrcoder
 */
@Service
@Validated
@Slf4j
public class SysNotifySendServiceImpl implements SysNotifySendService {

    @Resource
    private SysNotifyTemplateService notifyTemplateService;

    @Resource
    private SysNotifyMessageService notifyMessageService;

    @Override
    public String sendSingleNotifyToAdmin(String userId, String templateCode,
            Map<String, Object> templateParams) {
        return sendSingleNotify(userId, UserTypeEnum.ADMIN.getValue(), templateCode,
                templateParams);
    }

    @Override
    public String sendSingleNotifyToMember(String userId, String templateCode,
            Map<String, Object> templateParams) {
        return sendSingleNotify(userId, UserTypeEnum.MEMBER.getValue(), templateCode,
                templateParams);
    }

    @Override
    public String sendSingleNotify(String userId, Integer userType, String templateCode,
            Map<String, Object> templateParams) {
        // 校验模版
        SysNotifyTemplateDO template = validateNotifyTemplate(templateCode);
        if (Objects.equals(template.getStatus(), CommonStatusEnum.DISABLE.getStatus())) {
            log.info("[sendSingleNotify][模版({})已经关闭，无法给用户({}/{})发送]", templateCode, userId,
                    userType);
            return null;
        }
        // 校验参数
        validateTemplateParams(template, templateParams);

        // 发送站内信
        String content = notifyTemplateService.formatNotifyTemplateContent(template.getContent(),
                templateParams);
        return notifyMessageService.createNotifyMessage(userId, userType, template, content,
                templateParams);
    }

    @VisibleForTesting
    public SysNotifyTemplateDO validateNotifyTemplate(String templateCode) {
        SysNotifyTemplateDO template = notifyTemplateService.getOne(
                new LambdaQueryWrapper<SysNotifyTemplateDO>().eq(SysNotifyTemplateDO::getCode,
                        templateCode));
        // 站内信模板不存在
        if (template == null) {
            throw exception(NOTICE_NOT_FOUND);
        }
        return template;
    }

    /**
     * 校验站内信模版参数是否确实
     *
     * @param template       邮箱模板
     * @param templateParams 参数列表
     */
    @VisibleForTesting
    public void validateTemplateParams(SysNotifyTemplateDO template,
            Map<String, Object> templateParams) {
        template.getParams().forEach(key -> {
            Object value = templateParams.get(key);
            if (value == null) {
                throw exception(NOTIFY_SEND_TEMPLATE_PARAM_MISS, key);
            }
        });
    }
}
