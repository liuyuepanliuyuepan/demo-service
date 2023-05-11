package cn.klmb.crm.module.product.controller.admin.detail.vo;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * 产品上下架状态
 */
@Data
public class ProductStatusVO {

    @ApiModelProperty("产品bizId列表")
    @NotEmpty(message = "产品bizId列表不能为空")
    private List<String> bizIds;

    @ApiModelProperty("上下架状态(1上架  0下架)")
    @NotNull(message = "上下架状态不能为空")
    private Integer status;

}
