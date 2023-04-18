package cn.klmb.crm.module.product.convert.detail;

import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.module.product.controller.admin.detail.vo.ProductDetailPageReqVO;
import cn.klmb.crm.module.product.controller.admin.detail.vo.ProductDetailRespVO;
import cn.klmb.crm.module.product.controller.admin.detail.vo.ProductDetailSaveReqVO;
import cn.klmb.crm.module.product.controller.admin.detail.vo.ProductDetailUpdateReqVO;
import cn.klmb.crm.module.product.dto.detail.ProductDetailQueryDTO;
import cn.klmb.crm.module.product.entity.detail.ProductDetailDO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 产品 Convert
 *
 * @author 超级管理员
 */
@Mapper
public interface ProductDetailConvert {

    ProductDetailConvert INSTANCE = Mappers.getMapper(ProductDetailConvert.class);

    ProductDetailDO convert(ProductDetailSaveReqVO saveReqVO);

    ProductDetailDO convert(ProductDetailUpdateReqVO updateReqVO);

    KlmbPage<ProductDetailRespVO> convert(KlmbPage<ProductDetailDO> page);

    List<ProductDetailRespVO> convert(List<ProductDetailDO> list);

    ProductDetailRespVO convert(ProductDetailDO saveDO);

    ProductDetailQueryDTO convert(ProductDetailPageReqVO reqVO);

}
