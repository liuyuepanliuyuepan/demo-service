package cn.klmb.crm.module.member.controller.admin.contacts.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 联系人 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MemberContactsRespVO extends MemberContactsBaseVO {

    @ApiModelProperty(value = "业务id")
    private String bizId;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "客户名称")
    private String customerName;

    /**
     * 直属上级
     */
    @ApiModelProperty(value = "直属上级")
    private String parentContactsName;

    /**
     * 负责人
     */
    @ApiModelProperty(value = "负责人")
    private String ownerUserName;

    /**
     * 是否首要联系人
     */
    @ApiModelProperty(value = "是否首要联系人")
    private Boolean isFirstContacts;

}
