package cn.klmb.crm.module.system.entity.config;

import cn.klmb.crm.framework.base.core.entity.KlmbBaseDO;
import cn.klmb.crm.module.system.enums.config.SysConfigTypeEnum;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 系统管理-配置管理
 *
 * @author shilinchuan
 * @date 2022/12/5
 */
@TableName("sys_config")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class SysConfigDO extends KlmbBaseDO {

    /**
     * 参数分类
     */
    private String category;
    /**
     * 参数名称
     */
    private String name;
    /**
     * 参数键名
     */
    private String configKey;
    /**
     * 参数键值
     */
    private String value;
    /**
     * 参数类型
     * <p>
     * 枚举 {@link SysConfigTypeEnum}
     */
    private Integer type;
    /**
     * 是否可见
     * <p>
     * 不可见的参数，一般是敏感参数，前端不可获取
     */
    private Boolean visible;
    /**
     * 备注
     */
    private String remark;

}
