package cn.klmb.crm.module.system.controller.admin.file.vo.file;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 文件
 *
 * @author shilinchuan
 * @date 2022/12/6
 */
@ApiModel(description = "文件")
@Data
public class SysFileRespVO {

    @ApiModelProperty(value = "文件编号", required = true, example = "uuid")
    private String bizId;

    @ApiModelProperty(value = "原文件名", required = true, example = "klmb.jpg")
    private String name;

    @ApiModelProperty(value = "文件 URL", required = true, example = "https://www.xxx.cn/klmb.jpg")
    private String url;

    @ApiModelProperty(value = "文件MIME类型", example = "application/octet-stream")
    private String type;

    @ApiModelProperty(value = "文件大小", example = "2048", required = true)
    private Integer size;

    @ApiModelProperty(value = "创建时间", required = true)
    private LocalDateTime createTime;

}
