package cn.klmb.crm.module.member.controller.admin.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 客户-用户 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MemberUserRespVO extends MemberUserBaseVO {

    @ApiModelProperty(value = "业务id")
    private String bizId;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    /**
     * 负责人
     */
    @ApiModelProperty(value = "负责人")
    private String ownerUserName;

    /**
     * 首要联系人
     */
    @ApiModelProperty(value = "首要联系人")
    private String contactsName;

    /**
     * 首要联系人手机
     */
    @ApiModelProperty(value = "首要联系人手机")
    private String contactsMobile;

    /**
     * 是否标星
     */
    @ApiModelProperty(value = "是否标星")
    private Boolean star;

    /**
     * 是否在公海
     */
    @ApiModelProperty(value = "是否在公海")
    private Boolean existPool;

}
