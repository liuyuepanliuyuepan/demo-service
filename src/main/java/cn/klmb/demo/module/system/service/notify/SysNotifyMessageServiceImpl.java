package cn.klmb.demo.module.system.service.notify;


import cn.klmb.demo.framework.base.core.service.KlmbBaseServiceImpl;
import cn.klmb.demo.module.system.dao.notify.SysNotifyMessageMapper;
import cn.klmb.demo.module.system.dto.notify.SysNotifyMessageQueryDTO;
import cn.klmb.demo.module.system.entity.notify.SysNotifyMessageDO;
import cn.klmb.demo.module.system.entity.notify.SysNotifyTemplateDO;
import java.util.Map;
import org.springframework.stereotype.Service;


/**
 * 站内信 Service 实现类
 *
 * @author 超级管理员
 */
@Service
public class SysNotifyMessageServiceImpl extends
        KlmbBaseServiceImpl<SysNotifyMessageDO, SysNotifyMessageQueryDTO, SysNotifyMessageMapper> implements
        SysNotifyMessageService {

    public SysNotifyMessageServiceImpl(SysNotifyMessageMapper mapper) {
        this.mapper = mapper;
    }


    @Override
    public String createNotifyMessage(String userId, Integer userType,
            SysNotifyTemplateDO template, String templateContent,
            Map<String, Object> templateParams) {
        SysNotifyMessageDO message = new SysNotifyMessageDO().setUserId(userId)
                .setUserType(userType)
                .setTemplateId(template.getBizId()).setTemplateCode(template.getCode())
                .setTemplateNickname(template.getNickname())
                .setTemplateContent(templateContent).setTemplateParams(templateParams)
                .setReadStatus(false);
        super.saveDO(message);
        return message.getBizId();
    }

}
