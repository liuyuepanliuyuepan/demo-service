package cn.klmb.demo.module.system.controller.admin.file.vo.file;


import cn.klmb.demo.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 文件 - 分页查询
 *
 * @author liuyuepan
 * @date 2022/12/6
 */
@ApiModel(description = "文件 - 分页查询")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SysFilePageReqVO extends PageParam {

    @ApiModelProperty(value = "文件路径", example = "klmb", notes = "模糊匹配")
    private String path;

    @ApiModelProperty(value = "文件类型", example = "application/octet-stream", notes = "模糊匹配")
    private String type;

//    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
//    @ApiModelProperty(value = "创建时间")
//    private LocalDateTime[] createTime;

}
