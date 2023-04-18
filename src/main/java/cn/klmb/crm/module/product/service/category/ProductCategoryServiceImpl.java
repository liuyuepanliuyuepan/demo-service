package cn.klmb.crm.module.product.service.category;

import cn.klmb.crm.framework.base.core.service.KlmbBaseServiceImpl;
import cn.klmb.crm.module.product.dao.category.ProductCategoryMapper;
import cn.klmb.crm.module.product.dto.category.ProductCategoryQueryDTO;
import cn.klmb.crm.module.product.entity.category.ProductCategoryDO;
import org.springframework.stereotype.Service;


/**
 * 产品分类 Service 实现类
 *
 * @author 超级管理员
 */
@Service
public class ProductCategoryServiceImpl extends
        KlmbBaseServiceImpl<ProductCategoryDO, ProductCategoryQueryDTO, ProductCategoryMapper> implements
        ProductCategoryService {

    public ProductCategoryServiceImpl(ProductCategoryMapper mapper) {
        this.mapper = mapper;
    }

}
