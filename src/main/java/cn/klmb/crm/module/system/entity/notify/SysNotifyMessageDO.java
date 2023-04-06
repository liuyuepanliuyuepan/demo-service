package cn.klmb.crm.module.system.entity.notify;

import cn.klmb.crm.framework.base.core.entity.KlmbBaseDO;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * 站内信 DO
 *
 * @author 超级管理员
 */
@TableName(value = "sys_notify_message", autoResultMap = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class SysNotifyMessageDO extends KlmbBaseDO {

    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户类型(1会员,2管理员)
     */
    private Integer userType;
    /**
     * 模版编号
     */
    private String templateId;
    /**
     * 模版编码
     */
    private String templateCode;
    /**
     * 模版发送人名称
     */
    private String templateNickname;
    /**
     * 模版内容
     */
    private String templateContent;
    /**
     * 模版参数
     * <p>
     * 基于 {@link SysNotifyTemplateDO#getParams()} 输入后的参数
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> templateParams;
    /**
     * 是否已读
     */
    private Boolean readStatus;
    /**
     * 阅读时间
     */
    private LocalDateTime readTime;

}
