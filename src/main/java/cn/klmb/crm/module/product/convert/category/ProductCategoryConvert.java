package cn.klmb.crm.module.product.convert.category;

import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.module.product.controller.admin.category.vo.ProductCategoryPageReqVO;
import cn.klmb.crm.module.product.controller.admin.category.vo.ProductCategoryRespVO;
import cn.klmb.crm.module.product.controller.admin.category.vo.ProductCategorySaveReqVO;
import cn.klmb.crm.module.product.controller.admin.category.vo.ProductCategoryUpdateReqVO;
import cn.klmb.crm.module.product.dto.category.ProductCategoryQueryDTO;
import cn.klmb.crm.module.product.entity.category.ProductCategoryDO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 产品分类 Convert
 *
 * @author 超级管理员
 */
@Mapper
public interface ProductCategoryConvert {

    ProductCategoryConvert INSTANCE = Mappers.getMapper(ProductCategoryConvert.class);

    ProductCategoryDO convert(ProductCategorySaveReqVO saveReqVO);

    ProductCategoryDO convert(ProductCategoryUpdateReqVO updateReqVO);

    KlmbPage<ProductCategoryRespVO> convert(KlmbPage<ProductCategoryDO> page);

    List<ProductCategoryRespVO> convert(List<ProductCategoryDO> list);

    ProductCategoryRespVO convert(ProductCategoryDO saveDO);

    ProductCategoryQueryDTO convert(ProductCategoryPageReqVO reqVO);

}
