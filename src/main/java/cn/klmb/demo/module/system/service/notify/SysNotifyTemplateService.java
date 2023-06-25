package cn.klmb.demo.module.system.service.notify;


import cn.klmb.demo.framework.base.core.service.KlmbBaseService;
import cn.klmb.demo.module.system.controller.admin.notify.vo.template.SysNotifyTemplateSaveReqVO;
import cn.klmb.demo.module.system.controller.admin.notify.vo.template.SysNotifyTemplateUpdateReqVO;
import cn.klmb.demo.module.system.dto.notify.SysNotifyTemplateQueryDTO;
import cn.klmb.demo.module.system.entity.notify.SysNotifyTemplateDO;
import java.util.Map;
import javax.validation.Valid;

/**
 * 站内信模板 Service 接口
 *
 * @author 超级管理员
 */
public interface SysNotifyTemplateService extends
        KlmbBaseService<SysNotifyTemplateDO, SysNotifyTemplateQueryDTO> {


    /**
     * 创建站内信模版
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createNotifyTemplate(@Valid SysNotifyTemplateSaveReqVO createReqVO);


    /**
     * 更新站内信模版
     *
     * @param updateReqVO 更新信息
     */
    void updateNotifyTemplate(@Valid SysNotifyTemplateUpdateReqVO updateReqVO);


    /**
     * 删除站内信模版
     *
     * @param bizId 业务id
     */
    void deleteNotifyTemplate(String bizId);


    /**
     * 格式化站内信内容
     *
     * @param content 站内信模板的内容
     * @param params  站内信内容的参数
     * @return 格式化后的内容
     */
    String formatNotifyTemplateContent(String content, Map<String, Object> params);


    /**
     * 获得站内信模板，从缓存中
     *
     * @param code 模板编码
     * @return 站内信模板
     */
    SysNotifyTemplateDO getNotifyTemplateByCodeFromCache(String code);


}
