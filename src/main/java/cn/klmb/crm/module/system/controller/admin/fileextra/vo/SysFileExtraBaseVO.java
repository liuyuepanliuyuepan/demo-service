package cn.klmb.crm.module.system.controller.admin.fileextra.vo;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * CRM附件关联 Base VO，提供给添加、修改、详细的子 VO 使用 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class SysFileExtraBaseVO {

    @ApiModelProperty(value = "类型，同crm类型", example = "1")
    @NotNull(message = "类型不能为空")
    private Integer type;

    @ApiModelProperty(value = "对应类型主键ID", example = "15361")
    @NotBlank(message = "对应类型主键ID不能为空")
    private String typeId;

    @ApiModelProperty(value = "文件名", example = "李四")
    private String name;

    @ApiModelProperty(value = "附件大小")
    private Integer size;

    @ApiModelProperty(value = "来源(目前写死附件上传)")
    private String source;

    @ApiModelProperty(value = "文件id")
    @NotBlank(message = "文件id不能为空")
    private String fileId;

}
