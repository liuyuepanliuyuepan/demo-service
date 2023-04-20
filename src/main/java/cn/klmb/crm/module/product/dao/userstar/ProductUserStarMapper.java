package cn.klmb.crm.module.product.dao.userstar;

import cn.klmb.crm.framework.base.core.dao.KlmbBaseMapper;
import cn.klmb.crm.module.product.dto.userstar.ProductUserStarQueryDTO;
import cn.klmb.crm.module.product.entity.userstar.ProductUserStarDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户产品标星关系 Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface ProductUserStarMapper extends
        KlmbBaseMapper<ProductUserStarDO, ProductUserStarQueryDTO> {

}
