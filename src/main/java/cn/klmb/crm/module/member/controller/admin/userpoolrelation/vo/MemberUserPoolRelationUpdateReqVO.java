package cn.klmb.crm.module.member.controller.admin.userpoolrelation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 客户公海关联更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MemberUserPoolRelationUpdateReqVO extends MemberUserPoolRelationBaseVO {

    @ApiModelProperty(value = "业务id", required = true, example = "2501cb266cb44e679d7768c01854a92c")
    @NotNull(message = "业务id不能为空")
    private String bizId;

}
