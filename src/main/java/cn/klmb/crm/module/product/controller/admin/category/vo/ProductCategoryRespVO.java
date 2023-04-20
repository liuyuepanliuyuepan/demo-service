package cn.klmb.crm.module.product.controller.admin.category.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 产品分类 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProductCategoryRespVO extends ProductCategoryBaseVO {

    @ApiModelProperty(value = "业务id", required = true, example = "d3fa77161440451e9fd9cc628341afc0")
    private String bizId;

    @ApiModelProperty(value = "创建时间", required = true)
    private LocalDateTime createTime;

}
