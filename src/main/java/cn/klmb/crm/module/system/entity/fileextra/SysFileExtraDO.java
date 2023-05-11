package cn.klmb.crm.module.system.entity.fileextra;

import cn.klmb.crm.framework.base.core.entity.KlmbBaseDO;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * CRM附件关联 DO
 *
 * @author 超级管理员
 */
@TableName(value = "sys_file_extra", autoResultMap = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class SysFileExtraDO extends KlmbBaseDO {

    /**
     * 类型，同crm类型
     */
    private Integer type;
    /**
     * 对应类型主键ID
     */
    private String typeId;
    /**
     * 文件名
     */
    private String name;
    /**
     * 附件大小
     */
    private Integer size;
    /**
     * 来源(目前写死附件上传)
     */
    private String source;

    /**
     * 文件id
     */
    private String fileId;

    /**
     * 上传人
     */
    @TableField(exist = false)
    private String creatorName;


    @ApiModelProperty(value = "文件url")
    @TableField(exist = false)
    private String fileUrl;

    @ApiModelProperty(value = "文件类型")
    @TableField(exist = false)
    private String fileType;


}
