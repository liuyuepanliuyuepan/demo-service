package cn.klmb.crm.module.product.entity.category;

import cn.klmb.crm.framework.base.core.entity.KlmbBaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * 产品分类 DO
 *
 * @author 超级管理员
 */
@TableName(value = "product_category", autoResultMap = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class ProductCategoryDO extends KlmbBaseDO {

    /**
     * 产品分类名称
     */
    private String name;
    /**
     * 产品分类的父id
     */
    private String pid;

}
