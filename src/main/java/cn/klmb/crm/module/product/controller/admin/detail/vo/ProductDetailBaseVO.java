package cn.klmb.crm.module.product.controller.admin.detail.vo;

import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * 产品 Base VO，提供给添加、修改、详细的子 VO 使用 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class ProductDetailBaseVO {

    @ApiModelProperty(value = "产品名称", example = "李四")
    private String name;

    @ApiModelProperty(value = "产品编码", required = true)
    @NotNull(message = "产品编码不能为空")
    private String num;

    @ApiModelProperty(value = "单位")
    private String unit;

    @ApiModelProperty(value = "价格")
    private BigDecimal price;

    @ApiModelProperty(value = "产品分类ID", example = "30930")
    private String categoryId;

    @ApiModelProperty(value = "产品描述", example = "飞流直下三千尺，疑是银河落九天")
    private String description;

    @ApiModelProperty(value = "负责人ID", example = "28479")
    private String ownerUserId;

    @ApiModelProperty(value = "主图")
    private String mainFileIds;

    @ApiModelProperty(value = "详情图")
    private String detailFileIds;

}
