package cn.klmb.crm.module.product.service.userstar;

import cn.klmb.crm.framework.base.core.service.KlmbBaseServiceImpl;
import cn.klmb.crm.module.product.dao.userstar.ProductUserStarMapper;
import cn.klmb.crm.module.product.dto.userstar.ProductUserStarQueryDTO;
import cn.klmb.crm.module.product.entity.userstar.ProductUserStarDO;
import org.springframework.stereotype.Service;


/**
 * 用户产品标星关系 Service 实现类
 *
 * @author 超级管理员
 */
@Service
public class ProductUserStarServiceImpl extends
        KlmbBaseServiceImpl<ProductUserStarDO, ProductUserStarQueryDTO, ProductUserStarMapper> implements
        ProductUserStarService {

    public ProductUserStarServiceImpl(ProductUserStarMapper mapper) {
        this.mapper = mapper;
    }

}
