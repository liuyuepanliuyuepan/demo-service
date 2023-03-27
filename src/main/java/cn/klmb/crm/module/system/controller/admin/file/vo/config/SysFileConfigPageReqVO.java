package cn.klmb.crm.module.system.controller.admin.file.vo.config;


import cn.klmb.crm.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 文件配置 - 分页查询
 *
 * @author shilinchuan
 * @date 2022/12/6
 */
@ApiModel(description = "文件配置 - 分页查询")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SysFileConfigPageReqVO extends PageParam {

    @ApiModelProperty(value = "配置名", example = "S3 - 阿里云")
    private String name;

    @ApiModelProperty(value = "存储器", example = "1")
    private Integer storage;

//    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
//    @ApiModelProperty(value = "创建时间")
//    private LocalDateTime[] createTime;

}
