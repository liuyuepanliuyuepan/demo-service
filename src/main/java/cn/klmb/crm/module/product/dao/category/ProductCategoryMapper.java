package cn.klmb.crm.module.product.dao.category;

import cn.klmb.crm.framework.base.core.dao.KlmbBaseMapper;
import cn.klmb.crm.module.product.dto.category.ProductCategoryQueryDTO;
import cn.klmb.crm.module.product.entity.category.ProductCategoryDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 产品分类 Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface ProductCategoryMapper extends
        KlmbBaseMapper<ProductCategoryDO, ProductCategoryQueryDTO> {

}
