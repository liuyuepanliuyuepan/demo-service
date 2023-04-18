package cn.klmb.crm.module.product.dao.detail;

import cn.klmb.crm.framework.base.core.dao.KlmbBaseMapper;
import cn.klmb.crm.module.product.dto.detail.ProductDetailQueryDTO;
import cn.klmb.crm.module.product.entity.detail.ProductDetailDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 产品 Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface ProductDetailMapper extends
        KlmbBaseMapper<ProductDetailDO, ProductDetailQueryDTO> {

}
