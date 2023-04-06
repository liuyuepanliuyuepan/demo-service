package cn.klmb.crm.module.member.controller.admin.bis.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 客户工商信息更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MemberBisUpdateReqVO extends MemberBisBaseVO {

    @ApiModelProperty(value = "业务id", required = true, example = "7eff7296786d4152bd4b164ae0766f9e")
    @NotNull(message = "业务id不能为空")
    private String bizId;

}
