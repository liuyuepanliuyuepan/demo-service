package cn.klmb.crm.module.business.convert.product;

import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.module.business.controller.admin.product.vo.BusinessProductPageReqVO;
import cn.klmb.crm.module.business.controller.admin.product.vo.BusinessProductRespVO;
import cn.klmb.crm.module.business.controller.admin.product.vo.BusinessProductSaveReqVO;
import cn.klmb.crm.module.business.controller.admin.product.vo.BusinessProductUpdateReqVO;
import cn.klmb.crm.module.business.dto.product.BusinessProductQueryDTO;
import cn.klmb.crm.module.business.entity.product.BusinessProductDO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 商机产品关系 Convert
 *
 * @author 超级管理员
 */
@Mapper
public interface BusinessProductConvert {

    BusinessProductConvert INSTANCE = Mappers.getMapper(BusinessProductConvert.class);

    BusinessProductDO convert(BusinessProductSaveReqVO saveReqVO);

    BusinessProductDO convert(BusinessProductUpdateReqVO updateReqVO);

    KlmbPage<BusinessProductRespVO> convert(KlmbPage<BusinessProductDO> page);

    List<BusinessProductRespVO> convert(List<BusinessProductDO> list);

    BusinessProductRespVO convert(BusinessProductDO saveDO);

    BusinessProductQueryDTO convert(BusinessProductPageReqVO reqVO);

}
