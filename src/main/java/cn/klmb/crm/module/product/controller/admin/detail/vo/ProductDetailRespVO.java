package cn.klmb.crm.module.product.controller.admin.detail.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 产品 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProductDetailRespVO extends ProductDetailBaseVO {

    @ApiModelProperty(value = "业务id", required = true, example = "b2661a0914904461bc856333deff7ba3")
    private String bizId;

    @ApiModelProperty(value = "创建时间", required = true)
    private LocalDateTime createTime;

}
