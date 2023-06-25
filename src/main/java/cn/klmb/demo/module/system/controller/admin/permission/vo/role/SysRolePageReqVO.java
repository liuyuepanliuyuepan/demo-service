package cn.klmb.demo.module.system.controller.admin.permission.vo.role;


import static cn.klmb.demo.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

import cn.klmb.demo.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 系统角色 - 分页查询
 *
 * @author liuyuepan
 * @date 2022/11/30
 */
@ApiModel(description = "系统角色 - 分页查询")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SysRolePageReqVO extends PageParam {

    @ApiModelProperty(value = "角色名称", example = "管理员", notes = "模糊匹配")
    private String name;

    @ApiModelProperty(value = "角色标识", example = "角色编码", notes = "模糊匹配")
    private String code;

    @ApiModelProperty(value = "状态", example = "1")
    private Integer status;

    @ApiModelProperty(value = "创建时间", example = "2022-07-01 00:00:00,2022-07-01 23:59:59")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;


}
