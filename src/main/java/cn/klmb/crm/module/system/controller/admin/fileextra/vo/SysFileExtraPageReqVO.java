package cn.klmb.crm.module.system.controller.admin.fileextra.vo;

import static cn.klmb.crm.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

import cn.klmb.crm.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

@ApiModel("管理后台 - CRM附件关联分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SysFileExtraPageReqVO extends PageParam {

    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @ApiModelProperty(value = "类型，同crm类型", example = "1")
    private Integer type;

    @ApiModelProperty(value = "对应类型主键ID", example = "15361")
    private String typeId;

    @ApiModelProperty(value = "文件名", example = "李四")
    private String name;

    @ApiModelProperty(value = "附件大小")
    private Integer size;

    @ApiModelProperty(value = "来源(目前写死附件上传)")
    private String source;

    @ApiModelProperty(value = "文件id")
    private String fileId;

}
