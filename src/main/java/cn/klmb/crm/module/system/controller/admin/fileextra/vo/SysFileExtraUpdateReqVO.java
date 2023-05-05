package cn.klmb.crm.module.system.controller.admin.fileextra.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

@ApiModel("管理后台 - CRM附件关联更新 Request VO")
@Data
@ToString(callSuper = true)
public class SysFileExtraUpdateReqVO {

    @ApiModelProperty(value = "业务id", required = true, example = "c4ea7a14855c43439eb82839bf572940")
    @NotNull(message = "业务id不能为空")
    private String bizId;

    @ApiModelProperty(value = "文件名", example = "李四")
    @NotBlank(message = "文件名不能为空")
    private String name;

}
