package cn.klmb.crm.module.business.service.product;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.klmb.crm.framework.base.core.service.KlmbBaseServiceImpl;
import cn.klmb.crm.module.business.controller.admin.product.vo.BusinessProductRespVO;
import cn.klmb.crm.module.business.convert.product.BusinessProductConvert;
import cn.klmb.crm.module.business.dao.product.BusinessProductMapper;
import cn.klmb.crm.module.business.dto.product.BusinessProductQueryDTO;
import cn.klmb.crm.module.business.entity.product.BusinessProductDO;
import cn.klmb.crm.module.product.controller.admin.detail.vo.ProductDetailRespVO;
import cn.klmb.crm.module.product.service.detail.ProductDetailService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.util.List;
import java.util.stream.Collectors;
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

    private final ProductDetailService productDetailService;

    public BusinessProductServiceImpl(ProductDetailService productDetailService,
            BusinessProductMapper mapper) {
        this.productDetailService = productDetailService;
        this.mapper = mapper;
    }

    @Override
    public void removeBusinessProduct(List<String> businessIds) {
        List<BusinessProductDO> businessProductList = super.list(
                new LambdaQueryWrapper<BusinessProductDO>().in(BusinessProductDO::getBusinessId,
                        businessIds).eq(BusinessProductDO::getDeleted, false));
        if (CollUtil.isNotEmpty(businessProductList)) {
            List<String> collect = businessProductList.stream().map(BusinessProductDO::getBizId)
                    .collect(Collectors.toList());
            super.removeByBizIds(collect);
        }
    }

    @Override
    public List<BusinessProductRespVO> getBusinessProductByBusinessId(String businessId) {
        List<BusinessProductRespVO> convert = null;
        List<BusinessProductDO> businessProductList = super.list(
                new LambdaQueryWrapper<BusinessProductDO>().in(BusinessProductDO::getBusinessId,
                        businessId).eq(BusinessProductDO::getDeleted, false));
        if (CollUtil.isNotEmpty(businessProductList)) {
            convert = BusinessProductConvert.INSTANCE.convert(
                    businessProductList);
            if (CollUtil.isNotEmpty(convert)) {
                convert.forEach(e -> {
                    ProductDetailRespVO productDetailRespVO = productDetailService.getProductDetailByBizId(
                            e.getProductId());
                    e.setProductName(ObjectUtil.isNotNull(productDetailRespVO)
                            ? productDetailRespVO.getName() : null);
                    e.setCategoryName(ObjectUtil.isNotNull(productDetailRespVO)
                            ? productDetailRespVO.getCategoryName() : null);
                });
            }

        }
        return convert;
    }
}
