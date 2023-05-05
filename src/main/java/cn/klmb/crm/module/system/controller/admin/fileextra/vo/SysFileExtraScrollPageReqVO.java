package cn.klmb.crm.module.system.controller.admin.fileextra.vo;

import static cn.klmb.crm.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

import cn.klmb.crm.framework.base.core.pojo.PageScrollParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 管理后台 - CRM附件关联滚动分页查询
 *
 * @author liuyuepan
 * @date 2023/4/15
 */
@ApiModel(description = "管理后台 - CRM附件关联滚动分页查询")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SysFileExtraScrollPageReqVO extends PageScrollParam {

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
