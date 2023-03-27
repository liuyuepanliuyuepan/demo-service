package cn.klmb.crm.module.system.entity.file;

import cn.klmb.crm.framework.base.core.entity.KlmbBaseDO;
import cn.klmb.crm.framework.file.core.client.FileClientConfig;
import cn.klmb.crm.framework.file.core.enums.FileStorageEnum;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 系统管理-文件配置
 *
 * @author shilinchuan
 * @date 2022/12/5
 */
@TableName(value = "sys_file_config", autoResultMap = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class SysFileConfigDO extends KlmbBaseDO {

    /**
     * 配置名
     */
    private String name;
    /**
     * 存储器
     * <p>
     * 枚举 {@link FileStorageEnum}
     */
    private Integer storage;
    /**
     * 备注
     */
    private String remark;
    /**
     * 是否为主配置
     * <p>
     * 由于我们可以配置多个文件配置，默认情况下，使用主配置进行文件的上传
     */
    private Boolean master;

    /**
     * 文件配置
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private FileClientConfig config;

}
