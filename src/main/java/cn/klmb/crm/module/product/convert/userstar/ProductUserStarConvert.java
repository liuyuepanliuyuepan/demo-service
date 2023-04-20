package cn.klmb.crm.module.product.convert.userstar;

import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.module.product.controller.admin.userstar.vo.ProductUserStarPageReqVO;
import cn.klmb.crm.module.product.controller.admin.userstar.vo.ProductUserStarRespVO;
import cn.klmb.crm.module.product.controller.admin.userstar.vo.ProductUserStarSaveReqVO;
import cn.klmb.crm.module.product.controller.admin.userstar.vo.ProductUserStarUpdateReqVO;
import cn.klmb.crm.module.product.dto.userstar.ProductUserStarQueryDTO;
import cn.klmb.crm.module.product.entity.userstar.ProductUserStarDO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 用户产品标星关系 Convert
 *
 * @author 超级管理员
 */
@Mapper
public interface ProductUserStarConvert {

    ProductUserStarConvert INSTANCE = Mappers.getMapper(ProductUserStarConvert.class);

    ProductUserStarDO convert(ProductUserStarSaveReqVO saveReqVO);

    ProductUserStarDO convert(ProductUserStarUpdateReqVO updateReqVO);

    KlmbPage<ProductUserStarRespVO> convert(KlmbPage<ProductUserStarDO> page);

    List<ProductUserStarRespVO> convert(List<ProductUserStarDO> list);

    ProductUserStarRespVO convert(ProductUserStarDO saveDO);

    ProductUserStarQueryDTO convert(ProductUserStarPageReqVO reqVO);

}
