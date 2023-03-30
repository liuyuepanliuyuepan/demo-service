package cn.klmb.crm.module.member.controller.admin.contacts.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 联系人更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MemberContactsUpdateReqVO extends MemberContactsBaseVO {

    @ApiModelProperty(value = "业务id", required = true)
    @NotNull(message = "业务id不能为空")
    private String bizId;

}
