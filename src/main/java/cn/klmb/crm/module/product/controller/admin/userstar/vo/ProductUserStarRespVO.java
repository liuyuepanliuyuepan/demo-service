package cn.klmb.crm.module.product.controller.admin.userstar.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 用户产品标星关系 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProductUserStarRespVO extends ProductUserStarBaseVO {

    @ApiModelProperty(value = "业务id", required = true, example = "a38b982fc483490c84849c7124a94061")
    private String bizId;

    @ApiModelProperty(value = "创建时间", required = true)
    private LocalDateTime createTime;

}
