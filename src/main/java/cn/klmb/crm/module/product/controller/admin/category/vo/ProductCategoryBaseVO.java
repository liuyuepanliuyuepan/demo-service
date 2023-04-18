package cn.klmb.crm.module.product.controller.admin.category.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 产品分类 Base VO，提供给添加、修改、详细的子 VO 使用 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class ProductCategoryBaseVO {

    @ApiModelProperty(value = "产品分类名称", example = "赵六")
    private String name;

    @ApiModelProperty(value = "产品分类的父id", example = "14260")
    private String pid;

}
