package cn.klmb.crm.module.product.dto.category;

import cn.klmb.crm.framework.base.core.annotations.DtoFieldQuery;
import cn.klmb.crm.framework.base.core.annotations.DtoFieldQuery.Operator;
import cn.klmb.crm.framework.base.core.pojo.KlmbBaseQueryDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * 产品分类 DO
 *
 * @author 超级管理员
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class ProductCategoryQueryDTO extends KlmbBaseQueryDTO {

    /**
     * 产品分类名称
     */
    @DtoFieldQuery(queryType = Operator.LIKE)
    private String name;
    /**
     * 产品分类的父id
     */
    @DtoFieldQuery
    private String pid;

}
