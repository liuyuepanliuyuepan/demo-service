package cn.klmb.crm.module.member.controller.admin.team.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - crm团队成员更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MemberTeamUpdateReqVO extends MemberTeamBaseVO {

    @ApiModelProperty(value = "业务id", required = true, example = "32c53fbbec63483d84a73e7e44d8b3f9")
    @NotNull(message = "业务id不能为空")
    private String bizId;

}
