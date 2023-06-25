package cn.klmb.demo.module.system.controller.admin.user.vo;

import static cn.klmb.demo.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

import cn.klmb.demo.framework.base.core.pojo.PageScrollParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 系统用户 - 滚动分页查询
 *
 * @author liuyuepan
 * @date 2022/11/29
 */
@ApiModel(description = "系统用户 - 滚动分页查询")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SysUserScrollPageReqVO extends PageScrollParam {

    @ApiModelProperty(value = "用户账号", notes = "模糊匹配")
    private String username;

    @ApiModelProperty(value = "手机号码", notes = "模糊匹配")
    private String mobile;

    @ApiModelProperty(value = "状态", notes = "参见 CommonStatusEnum 枚举类")
    private Integer status;

    @ApiModelProperty(value = "创建时间", example = "2022-07-01 00:00:00,2022-07-01 23:59:59")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @ApiModelProperty(value = "部门编号", example = "1", notes = "同时筛选子部门")
    private String deptId;

}
