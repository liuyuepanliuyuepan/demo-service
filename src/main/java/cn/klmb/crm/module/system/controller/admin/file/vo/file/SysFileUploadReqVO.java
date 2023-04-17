package cn.klmb.crm.module.system.controller.admin.file.vo.file;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件 - 更新
 *
 * @author shilinchuan
 * @date 2022/12/3
 */
@ApiModel(description = "文件 - 更新")
@Data
public class SysFileUploadReqVO {

    @ApiModelProperty(value = "文件附件", required = true)
    @NotNull(message = "文件附件不能为空")
    private MultipartFile file;

    @ApiModelProperty(value = "文件附件", example = "klmb.png")
    private String path;

    @ApiModelProperty(value = "资源名称")
    private String resourceName;

}
