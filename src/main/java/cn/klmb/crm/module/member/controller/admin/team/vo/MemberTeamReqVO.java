package cn.klmb.crm.module.member.controller.admin.team.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@ApiModel("管理后台 - crm团队成员 Request VO")
@Data
@Builder
@ToString(callSuper = true)
public class MemberTeamReqVO {

    @ApiModelProperty(value = "类型，同crm类型(1 线索 2 客户 3 联系人 4 产品 5 商机 6 合同 7回款)")
    @NotNull(message = "类型不能为空")
    private Integer type;

    @ApiModelProperty(value = "对应类型主键ID")
    @NotBlank(message = "对应类型主键ID不能为空")
    private String typeId;

}
