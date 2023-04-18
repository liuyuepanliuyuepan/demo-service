package cn.klmb.crm.module.business.dao.product;

import cn.klmb.crm.framework.base.core.dao.KlmbBaseMapper;
import cn.klmb.crm.module.business.dto.product.BusinessProductQueryDTO;
import cn.klmb.crm.module.business.entity.product.BusinessProductDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商机产品关系 Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface BusinessProductMapper extends
        KlmbBaseMapper<BusinessProductDO, BusinessProductQueryDTO> {

}
