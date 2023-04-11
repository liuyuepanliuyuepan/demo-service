package cn.klmb.crm.module.member.controller.admin.userpool.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 公海更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MemberUserPoolUpdateReqVO extends MemberUserPoolBaseVO {

    @ApiModelProperty(value = "业务id", required = true, example = "71485e9c319e44218573e896015b8136")
    @NotNull(message = "业务id不能为空")
    private String bizId;

}
