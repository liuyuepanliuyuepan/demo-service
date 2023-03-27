package cn.klmb.crm.module.system.entity.sms;

import cn.klmb.crm.framework.base.core.entity.KlmbBaseDO;
import cn.klmb.crm.framework.common.enums.CommonStatusEnum;
import cn.klmb.crm.module.system.enums.sms.SysSmsTemplateTypeEnum;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 系统管理-短信模板
 *
 * @author shilinchuan
 * @date 2022/12/8
 */
@TableName(value = "sys_sms_template", autoResultMap = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class SysSmsTemplateDO extends KlmbBaseDO {

    // ========= 模板相关字段 =========

    /**
     * 短信类型
     * <p>
     * 枚举 {@link SysSmsTemplateTypeEnum}
     */
    private Integer type;
    /**
     * 启用状态
     * <p>
     * 枚举 {@link CommonStatusEnum}
     */
    private Integer status;
    /**
     * 模板编码，保证唯一
     */
    private String code;
    /**
     * 模板名称
     */
    private String name;
    /**
     * 模板内容
     * <p>
     * 内容的参数，使用 {} 包括，例如说 {name}
     */
    private String content;
    /**
     * 参数数组(自动根据内容生成)
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> params;
    /**
     * 备注
     */
    private String remark;
    /**
     * 短信 API 的模板编号
     */
    private String apiTemplateId;

    // ========= 渠道相关字段 =========

    /**
     * 短信渠道编号
     * <p>
     * 关联 {@link SysSmsChannelDO#getId()}
     */
    private String channelId;
    /**
     * 短信渠道编码
     * <p>
     * 冗余 {@link SysSmsChannelDO#getCode()}
     */
    private String channelCode;

}
