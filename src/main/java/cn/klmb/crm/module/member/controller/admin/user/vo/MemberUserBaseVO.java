package cn.klmb.crm.module.member.controller.admin.user.vo;

import static cn.klmb.crm.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 客户-用户 Base VO，提供给添加、修改、详细的子 VO 使用 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class MemberUserBaseVO {

    @ApiModelProperty(value = "客户名称", required = true, example = "张三")
    @NotNull(message = "客户名称不能为空")
    private String name;

    @ApiModelProperty(value = "客户级别 1(重点客户)，2(普通用户)，3(非优先客户)", required = true)
    @NotNull(message = "客户级别 1(重点客户)，2(普通用户)，3(非优先客户)不能为空")
    private Integer level;

    @ApiModelProperty(value = "客户来源")
    private Integer source;

    @ApiModelProperty(value = "下次联系时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime nextTime;

    @ApiModelProperty(value = "成交状态 0 未成交 1 已成交", example = "2")
    private Integer dealStatus;

    @ApiModelProperty(value = "成交时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime dealTime;

    @ApiModelProperty(value = "首要联系人ID", example = "17726")
    private String contactsId;

    @ApiModelProperty(value = "手机")
    private String mobile;

    @ApiModelProperty(value = "电话")
    private String tel;

    @ApiModelProperty(value = "网址")
    private String website;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "备注", example = "朝辞白帝彩云间，千里江陵一日还")
    private String remark;

    @ApiModelProperty(value = "负责人ID", example = "20584")
    private String ownerUserId;

    @ApiModelProperty(value = "省市区")
    private String address;

    @ApiModelProperty(value = "定位信息")
    private String location;

    @ApiModelProperty(value = "详细地址")
    private String detailAddress;

    @ApiModelProperty(value = "地理位置经度")
    private String lng;

    @ApiModelProperty(value = "地理位置纬度")
    private String lat;

    @ApiModelProperty(value = "最后跟进时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime lastTime;

    @ApiModelProperty(value = "放入公海时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime poolTime;

    @ApiModelProperty(value = "1 分配 2 领取")
    private Integer isReceive;

    @ApiModelProperty(value = "最后一条跟进记录")
    private String lastContent;

    @ApiModelProperty(value = "跟进状态(0未跟进1已跟进)")
    private Integer followup;

    @ApiModelProperty(value = "上级客户id", required = true, example = "24980")
    private String pid;

    /**
     * 客户行业
     */
    @ApiModelProperty(value = "客户行业")
    private Integer industry;
}
