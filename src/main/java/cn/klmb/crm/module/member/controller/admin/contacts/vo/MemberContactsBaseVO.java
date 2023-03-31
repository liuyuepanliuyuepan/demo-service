package cn.klmb.crm.module.member.controller.admin.contacts.vo;

import static cn.klmb.crm.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 联系人 Base VO，提供给添加、修改、详细的子 VO 使用 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class MemberContactsBaseVO {

    @ApiModelProperty(value = "联系人名称", example = "王五")
    @NotNull(message = "联系人名称不能为空")
    private String name;

    @ApiModelProperty(value = "客户id")
    @NotNull(message = "客户id不能为空")
    private String customerId;

    @ApiModelProperty(value = "下次联系时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime nextTime;

    @ApiModelProperty(value = "手机")
    private String mobile;

    @ApiModelProperty(value = "电话")
    private String tel;

    @ApiModelProperty(value = "电子邮箱")
    private String email;

    @ApiModelProperty(value = "职务")
    private String post;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "负责人ID")
    private String ownerUserId;

    @ApiModelProperty(value = "最后跟进时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime lastTime;

    /**
     * 直属上级id
     */
    @ApiModelProperty(value = "直属上级id")
    private String parentContactsId;


    /**
     * 联系人性别
     */
    @ApiModelProperty(value = "联系人性别(1男，2女，3未知)")
    private Integer sex;

}
