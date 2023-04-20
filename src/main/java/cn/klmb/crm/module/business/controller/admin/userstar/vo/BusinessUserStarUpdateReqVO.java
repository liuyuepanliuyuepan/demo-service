package cn.klmb.crm.module.business.controller.admin.userstar.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 用户商机标星关系更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BusinessUserStarUpdateReqVO extends BusinessUserStarBaseVO {

    @ApiModelProperty(value = "业务id", required = true, example = "4d34612a4a1c42fe8afe004bb4a23fcd")
    @NotNull(message = "业务id不能为空")
    private String bizId;

}
