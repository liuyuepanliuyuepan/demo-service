package cn.klmb.crm.module.system.entity.file;

import cn.klmb.crm.framework.base.core.entity.KlmbBaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 系统管理-文件
 *
 * @author shilinchuan
 * @date 2022/12/6
 */
@TableName("sys_file")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class SysFileDO extends KlmbBaseDO {

    /**
     * 配置编号
     * <p>
     * 关联 {@link SysFileConfigDO#getBizId()}
     */
    private String configId;
    /**
     * 原文件名
     */
    private String name;
    /**
     * 路径，即文件名
     */
    private String path;
    /**
     * 访问地址
     */
    private String url;
    /**
     * 文件的 MIME 类型，例如 "application/octet-stream"
     */
    private String type;
    /**
     * 文件大小
     */
    private Integer size;

}
