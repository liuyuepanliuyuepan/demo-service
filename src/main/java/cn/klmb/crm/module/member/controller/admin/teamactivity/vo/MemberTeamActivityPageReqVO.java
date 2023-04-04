package cn.klmb.crm.module.member.controller.admin.teamactivity.vo;

import static cn.klmb.crm.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

import cn.klmb.crm.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

@ApiModel("管理后台 - crm团队成员活动分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MemberTeamActivityPageReqVO extends PageParam {

    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @ApiModelProperty(value = "活动类型 1 跟进记录 2 创建记录 3 商机阶段变更 4 外勤签到", example = "1")
    private Integer type;

    @ApiModelProperty(value = "跟进类型(例如打电话)")
    private Integer category;

    @ApiModelProperty(value = "活动类型 1 线索 2 客户 3 联系人 4 产品 5 商机 6 合同 7回款 8日志 9审批 10日程 11任务 12 发邮件", example = "2")
    private Integer activityType;

    @ApiModelProperty(value = "活动类型Id", example = "27397")
    private String activityTypeId;

    @ApiModelProperty(value = "活动内容")
    private String content;

    @ApiModelProperty(value = "关联商机")
    private String businessIds;

    @ApiModelProperty(value = "关联联系人")
    private String contactsIds;


}
