package cn.klmb.crm.module.product.service.detail;

import cn.klmb.crm.framework.base.core.service.KlmbBaseServiceImpl;
import cn.klmb.crm.module.product.dao.detail.ProductDetailMapper;
import cn.klmb.crm.module.product.dto.detail.ProductDetailQueryDTO;
import cn.klmb.crm.module.product.entity.detail.ProductDetailDO;
import org.springframework.stereotype.Service;


/**
 * 产品 Service 实现类
 *
 * @author 超级管理员
 */
@Service
public class ProductDetailServiceImpl extends
        KlmbBaseServiceImpl<ProductDetailDO, ProductDetailQueryDTO, ProductDetailMapper> implements
        ProductDetailService {

    public ProductDetailServiceImpl(ProductDetailMapper mapper) {
        this.mapper = mapper;
    }

}
