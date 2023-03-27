package cn.klmb.crm.module.system.controller.admin.sms.vo.channel;


import static cn.klmb.crm.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

import cn.klmb.crm.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 系统管理 - 短信渠道 - 分页查询
 *
 * @author shilinchuan
 * @date 2022/12/8
 */
@ApiModel(description = "系统管理 - 短信渠道 - 分页查询")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SysSmsChannelPageReqVO extends PageParam {

    @ApiModelProperty(value = "任务状态", example = "1")
    private Integer status;

    @ApiModelProperty(value = "短信签名", example = "快乐萌宝", notes = "模糊匹配")
    private String signature;

    @ApiModelProperty(value = "创建时间", example = "2022-07-01 00:00:00,2022-07-01 23:59:59")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
