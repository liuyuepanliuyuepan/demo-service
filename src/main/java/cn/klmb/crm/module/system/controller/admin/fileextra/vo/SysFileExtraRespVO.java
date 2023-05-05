package cn.klmb.crm.module.system.controller.admin.fileextra.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - CRM附件关联 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SysFileExtraRespVO extends SysFileExtraBaseVO {

    @ApiModelProperty(value = "业务id", required = true, example = "c4ea7a14855c43439eb82839bf572940")
    private String bizId;

    @ApiModelProperty(value = "创建时间", required = true)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "上传人")
    private String creatorName;

    @ApiModelProperty(value = "文件url")
    private String fileUrl;

    @ApiModelProperty(value = "文件类型")
    private String fileType;


}
