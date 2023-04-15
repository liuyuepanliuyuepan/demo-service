package cn.klmb.crm.module.member.controller.admin.teamactivity.vo;

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
 * 系统用户 - 滚动分页查询
 *
 * @author shilinchuan
 * @date 2022/11/29
 */
@ApiModel(description = "管理后台 - crm团队成员活动更新 - 滚动分页查询")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MemberTeamActivityScrollPageReqVO extends PageScrollParam {

    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @ApiModelProperty(value = "活动类型 1 跟进记录 2 创建记录 3 商机阶段变更 4 外勤签到", example = "1")
    private Integer type;

    @ApiModelProperty(value = "活动类型 1 线索 2 客户 3 联系人 4 产品 5 商机 6 合同 7回款 8日志 9审批 10日程 11任务 12 发邮件", example = "2")
    private Integer activityType;

    @ApiModelProperty(value = "活动类型Id")
    private String activityTypeId;

    @ApiModelProperty(value = "活动内容")
    private String content;

}
