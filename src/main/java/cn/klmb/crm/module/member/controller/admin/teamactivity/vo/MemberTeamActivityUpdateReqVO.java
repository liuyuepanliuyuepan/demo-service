package cn.klmb.crm.module.member.controller.admin.teamactivity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - crm团队成员活动更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MemberTeamActivityUpdateReqVO extends MemberTeamActivityBaseVO {

    @ApiModelProperty(value = "业务id", required = true, example = "8991f5d29b4549b686d91cfef1a63fd4")
    @NotNull(message = "业务id不能为空")
    private String bizId;

}
