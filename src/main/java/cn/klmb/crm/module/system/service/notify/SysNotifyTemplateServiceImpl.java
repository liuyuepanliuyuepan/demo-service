package cn.klmb.crm.module.system.service.notify;


import static cn.klmb.crm.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.klmb.crm.module.system.enums.ErrorCodeConstants.NOTIFY_TEMPLATE_CODE_DUPLICATE;
import static cn.klmb.crm.module.system.enums.ErrorCodeConstants.NOTIFY_TEMPLATE_NOT_EXISTS;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.klmb.crm.framework.base.core.service.KlmbBaseServiceImpl;
import cn.klmb.crm.framework.mq.core.producer.notify.NotifyProducer;
import cn.klmb.crm.module.system.controller.admin.notify.vo.template.SysNotifyTemplateSaveReqVO;
import cn.klmb.crm.module.system.controller.admin.notify.vo.template.SysNotifyTemplateUpdateReqVO;
import cn.klmb.crm.module.system.convert.notify.SysNotifyTemplateConvert;
import cn.klmb.crm.module.system.dao.notify.SysNotifyTemplateMapper;
import cn.klmb.crm.module.system.dto.notify.SysNotifyTemplateQueryDTO;
import cn.klmb.crm.module.system.entity.notify.SysNotifyTemplateDO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.annotations.VisibleForTesting;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.springframework.stereotype.Service;


/**
 * 站内信模板 Service 实现类
 *
 * @author 超级管理员
 */
@Service
public class SysNotifyTemplateServiceImpl extends
        KlmbBaseServiceImpl<SysNotifyTemplateDO, SysNotifyTemplateQueryDTO, SysNotifyTemplateMapper> implements
        SysNotifyTemplateService {

    /**
     * 正则表达式，匹配 {} 中的变量
     */
    private static final Pattern PATTERN_PARAMS = Pattern.compile("\\{(.*?)}");
    private final NotifyProducer notifyProducer;
    /**
     * 站内信模板缓存 key：站内信模板编码 {@link SysNotifyTemplateDO#getCode()}
     * <p>
     * 这里声明 volatile 修饰的原因是，每次刷新时，直接修改指向
     */
    private volatile Map<String, SysNotifyTemplateDO> notifyTemplateCache;

    public SysNotifyTemplateServiceImpl(NotifyProducer notifyProducer,
            SysNotifyTemplateMapper mapper) {
        this.notifyProducer = notifyProducer;
        this.mapper = mapper;
    }

    @Override
    public String createNotifyTemplate(SysNotifyTemplateSaveReqVO createReqVO) {
        // 校验站内信编码是否重复
        validateNotifyTemplateCodeDuplicate(null, createReqVO.getCode());

        // 插入
        SysNotifyTemplateDO notifyTemplate = SysNotifyTemplateConvert.INSTANCE.convert(createReqVO);
        notifyTemplate.setParams(parseTemplateContentParams(notifyTemplate.getContent()));
        super.saveDO(notifyTemplate);

        // 发送刷新消息
        notifyProducer.sendNotifyTemplateRefreshMessage();
        return notifyTemplate.getBizId();
    }


    @Override
    public void updateNotifyTemplate(SysNotifyTemplateUpdateReqVO updateReqVO) {
        // 校验存在
        validateNotifyTemplateExists(updateReqVO.getBizId());
        // 校验站内信编码是否重复
        validateNotifyTemplateCodeDuplicate(updateReqVO.getBizId(), updateReqVO.getCode());

        // 更新
        SysNotifyTemplateDO updateObj = SysNotifyTemplateConvert.INSTANCE.convert(updateReqVO);
        updateObj.setParams(parseTemplateContentParams(updateObj.getContent()));
        super.updateDO(updateObj);

        // 发送刷新消息
        notifyProducer.sendNotifyTemplateRefreshMessage();
    }

    @Override
    public void deleteNotifyTemplate(String bizId) {
        // 校验存在
        validateNotifyTemplateExists(bizId);
        // 删除
        super.removeByBizId(bizId);
        // 发送刷新消息
        notifyProducer.sendNotifyTemplateRefreshMessage();
    }

    /**
     * 格式化站内信内容
     *
     * @param content 站内信模板的内容
     * @param params  站内信内容的参数
     * @return 格式化后的内容
     */
    @Override
    public String formatNotifyTemplateContent(String content, Map<String, Object> params) {
        return StrUtil.format(content, params);
    }

    @Override
    public SysNotifyTemplateDO getNotifyTemplateByCodeFromCache(String code) {
        return notifyTemplateCache.get(code);
    }


    @VisibleForTesting
    public void validateNotifyTemplateCodeDuplicate(String bizId, String code) {
        SysNotifyTemplateDO template = super.getOne(
                new LambdaQueryWrapper<SysNotifyTemplateDO>().eq(SysNotifyTemplateDO::getCode,
                        code));
        if (template == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的字典类型
        if (bizId == null) {
            throw exception(NOTIFY_TEMPLATE_CODE_DUPLICATE, code);
        }
        if (!template.getBizId().equals(bizId)) {
            throw exception(NOTIFY_TEMPLATE_CODE_DUPLICATE, code);
        }
    }

    @VisibleForTesting
    public List<String> parseTemplateContentParams(String content) {
        return ReUtil.findAllGroup1(PATTERN_PARAMS, content);
    }


    private void validateNotifyTemplateExists(String bizId) {
        if (super.getByBizId(bizId) == null) {
            throw exception(NOTIFY_TEMPLATE_NOT_EXISTS);
        }
    }


}
