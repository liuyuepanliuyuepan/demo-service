package cn.klmb.crm.module.business.service.product;

import cn.klmb.crm.framework.base.core.service.KlmbBaseService;
import cn.klmb.crm.module.business.dto.product.BusinessProductQueryDTO;
import cn.klmb.crm.module.business.entity.product.BusinessProductDO;
import java.util.List;


/**
 * 商机产品关系 Service 接口
 *
 * @author 超级管理员
 */
public interface BusinessProductService extends
        KlmbBaseService<BusinessProductDO, BusinessProductQueryDTO> {

    /**
     * 根据businessIds逻辑删除其商机产品关系
     *
     * @param businessIds
     */
    void removeBusinessProduct(List<String> businessIds);

}
