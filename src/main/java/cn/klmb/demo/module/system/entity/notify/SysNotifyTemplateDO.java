package cn.klmb.demo.module.system.entity.notify;

import cn.klmb.demo.framework.base.core.entity.KlmbBaseDO;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * 站内信模板 DO
 *
 * @author 超级管理员
 */
@TableName(value = "sys_notify_template", autoResultMap = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class SysNotifyTemplateDO extends KlmbBaseDO {

    /**
     * 模版名称
     */
    private String name;
    /**
     * 模版编码
     */
    private String code;
    /**
     * 模版类型
     */
    private Integer type;
    /**
     * 发送人名称
     */
    private String nickname;
    /**
     * 模版内容
     */
    private String content;
    /**
     * 参数数组
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> params;
    /**
     * 备注
     */
    private String remark;

}
