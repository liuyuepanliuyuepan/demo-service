package cn.klmb.crm.module.product.controller.admin.userstar.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 用户产品标星关系更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProductUserStarUpdateReqVO extends ProductUserStarBaseVO {

    @ApiModelProperty(value = "业务id", required = true, example = "a38b982fc483490c84849c7124a94061")
    @NotNull(message = "业务id不能为空")
    private String bizId;

}
