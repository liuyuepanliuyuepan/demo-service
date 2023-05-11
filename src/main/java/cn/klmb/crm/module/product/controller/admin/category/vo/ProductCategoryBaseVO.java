package cn.klmb.crm.module.product.controller.admin.category.vo;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 产品分类 Base VO，提供给添加、修改、详细的子 VO 使用 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class ProductCategoryBaseVO {

    @ApiModelProperty(value = "产品分类名称")
    @NotBlank(message = "产品分类名称不能为空！")
    private String name;

    @ApiModelProperty(value = "产品分类的父id(如果要建一级分类 pid传0)")
    @NotBlank(message = "产品分类的父id不能为空！")
    private String pid;

}
