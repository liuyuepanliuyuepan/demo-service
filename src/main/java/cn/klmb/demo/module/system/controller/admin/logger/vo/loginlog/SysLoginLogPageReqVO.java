package cn.klmb.demo.module.system.controller.admin.logger.vo.loginlog;


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
 * 系统管理 - 登录日志 - 分页查询
 *
 * @author liuyuepan
 * @date 2022/12/5
 */
@ApiModel(description = "系统管理 - 登录日志 - 分页查询")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SysLoginLogPageReqVO extends PageParam {

    @ApiModelProperty(value = "用户 IP", example = "127.0.0.1", notes = "模拟匹配")
    private String userIp;

    @ApiModelProperty(value = "用户账号", example = "快乐萌宝", notes = "模拟匹配")
    private String username;

    @ApiModelProperty(value = "操作状态", example = "true")
    private Boolean operateStatus;

    @ApiModelProperty(value = "登录时间", example = "2022-07-01 00:00:00,2022-07-01 23:59:59")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
