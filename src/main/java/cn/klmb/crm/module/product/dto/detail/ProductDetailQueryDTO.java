package cn.klmb.crm.module.product.dto.detail;

import cn.klmb.crm.framework.base.core.annotations.DtoFieldQuery;
import cn.klmb.crm.framework.base.core.annotations.DtoFieldQuery.Operator;
import cn.klmb.crm.framework.base.core.pojo.KlmbBaseQueryDTO;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * 产品 DO
 *
 * @author 超级管理员
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class ProductDetailQueryDTO extends KlmbBaseQueryDTO {

    /**
     * 产品名称
     */
    @DtoFieldQuery(queryType = Operator.LIKE)
    private String name;
    /**
     * 产品编码
     */
    @DtoFieldQuery
    private String num;
    /**
     * 单位
     */
    @DtoFieldQuery
    private String unit;
    /**
     * 价格
     */
    @DtoFieldQuery
    private BigDecimal price;
    /**
     * 产品分类ID
     */
    @DtoFieldQuery
    private String categoryId;
    /**
     * 产品描述
     */
    @DtoFieldQuery
    private String description;
    /**
     * 负责人ID
     */
    @DtoFieldQuery
    private String ownerUserId;
    /**
     * 主图
     */
    @DtoFieldQuery
    private String mainFileIds;
    /**
     * 详情图
     */
    @DtoFieldQuery
    private String detailFileIds;

}
