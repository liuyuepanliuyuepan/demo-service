package cn.klmb.crm.module.business.service.product;

import cn.klmb.crm.framework.base.core.service.KlmbBaseServiceImpl;
import cn.klmb.crm.module.business.dao.product.BusinessProductMapper;
import cn.klmb.crm.module.business.dto.product.BusinessProductQueryDTO;
import cn.klmb.crm.module.business.entity.product.BusinessProductDO;
import org.springframework.stereotype.Service;


/**
 * 商机产品关系 Service 实现类
 *
 * @author 超级管理员
 */
@Service
public class BusinessProductServiceImpl extends
        KlmbBaseServiceImpl<BusinessProductDO, BusinessProductQueryDTO, BusinessProductMapper> implements
        BusinessProductService {

    public BusinessProductServiceImpl(BusinessProductMapper mapper) {
        this.mapper = mapper;
    }

}
