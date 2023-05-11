package cn.klmb.crm.module.member.controller.admin.teamactivity.vo;

import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * crm团队成员活动 Base VO，提供给添加、修改、详细的子 VO 使用 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class MemberTeamActivityBaseVO {

    @ApiModelProperty(value = "活动类型 1 跟进记录 2 创建记录 3 商机阶段变更 4 外勤签到")
    @NotNull(message = "type不能为空")
    private Integer type;

    @ApiModelProperty(value = "跟进类型(例如打电话)")
    private String category;

    @ApiModelProperty(value = "活动类型 1 线索 2 客户 3 联系人 4 产品 5 商机 6 合同 7回款 8日志 9审批 10日程 11任务 12 发邮件")
    @NotNull(message = "活动类型不能为空！")
    private Integer activityType;

    @ApiModelProperty(value = "活动类型的bizId")
    @NotBlank(message = "活动类型的bizId！")
    private String activityTypeId;

    @ApiModelProperty(value = "活动内容")
    @NotBlank(message = "活动内容不能为空！")
    private String content;

    @ApiModelProperty(value = "关联商机")
    private String businessIds;

    @ApiModelProperty(value = "关联联系人")
    private String contactsIds;

    @ApiModelProperty(value = "下次联系时间")
    private LocalDateTime nextTime;

    @ApiModelProperty(value = "经度(type为4外勤签到时必传)")
    private String lng;

    @ApiModelProperty(value = "纬度(type为4外勤签到时必传)")
    private String lat;

    @ApiModelProperty(value = "签到地址(type为4外勤签到时必传)")
    private String address;

    /**
     * 图片ids
     */
    @ApiModelProperty(value = "图片ids")
    private List<String> imgIds;

    /**
     * 文件ids
     */
    @ApiModelProperty(value = "文件ids")
    private List<String> fileIds;

}
