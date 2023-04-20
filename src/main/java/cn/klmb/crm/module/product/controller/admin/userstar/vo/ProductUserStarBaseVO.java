package cn.klmb.crm.module.product.controller.admin.userstar.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户产品标星关系 Base VO，提供给添加、修改、详细的子 VO 使用 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class ProductUserStarBaseVO {

    @ApiModelProperty(value = "产品id", example = "1463")
    private String productId;

    @ApiModelProperty(value = "用户id", example = "18059")
    private String userId;

}
