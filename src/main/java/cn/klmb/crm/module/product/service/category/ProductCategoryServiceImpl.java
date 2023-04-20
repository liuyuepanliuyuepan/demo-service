package cn.klmb.crm.module.product.service.category;

import static cn.klmb.crm.framework.common.exception.util.ServiceExceptionUtil.exception;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.klmb.crm.framework.base.core.service.KlmbBaseServiceImpl;
import cn.klmb.crm.framework.common.util.data.RecursionUtil;
import cn.klmb.crm.module.product.controller.admin.category.vo.CrmProductCategoryBO;
import cn.klmb.crm.module.product.dao.category.ProductCategoryMapper;
import cn.klmb.crm.module.product.dto.category.ProductCategoryQueryDTO;
import cn.klmb.crm.module.product.entity.category.ProductCategoryDO;
import cn.klmb.crm.module.product.entity.detail.ProductDetailDO;
import cn.klmb.crm.module.product.enums.ErrorCodeConstants;
import cn.klmb.crm.module.product.service.detail.ProductDetailService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;


/**
 * 产品分类 Service 实现类
 *
 * @author 超级管理员
 */
@Service
public class ProductCategoryServiceImpl extends
        KlmbBaseServiceImpl<ProductCategoryDO, ProductCategoryQueryDTO, ProductCategoryMapper> implements
        ProductCategoryService {

    private final ProductDetailService productDetailService;

    public ProductCategoryServiceImpl(ProductDetailService productDetailService,
            ProductCategoryMapper mapper) {
        this.productDetailService = productDetailService;
        this.mapper = mapper;
    }


    @Override
    public List<CrmProductCategoryBO> queryList(String type) {
        List<ProductCategoryDO> categoryList = super.list(
                ProductCategoryQueryDTO.builder().bizId("dd5e47369bbe4b85b78992f0e466e275")
                        .build());
        if (StrUtil.isEmpty(type)) {
            return categoryList.stream()
                    .map(obj -> BeanUtil.copyProperties(obj, CrmProductCategoryBO.class)).collect(
                            Collectors.toList());
        }
        return RecursionUtil.getChildListTree(categoryList, "pid", "0", "bizId", "children",
                CrmProductCategoryBO.class);
    }


    @Override
    public void removeByBizIds(List<String> bizIds) {
        if (CollUtil.isEmpty(bizIds)) {
            return;
        }
        List<ProductCategoryDO> entities = this.listByBizIds(bizIds);
        if (CollUtil.isEmpty(entities)) {
            return;
        }
        //判断删除类别中是否已关联产品
        List<ProductDetailDO> productDetailDOS = productDetailService.list(
                new LambdaQueryWrapper<ProductDetailDO>().in(ProductDetailDO::getCategoryId, bizIds)
                        .eq(ProductDetailDO::getDeleted, false));
        if (CollUtil.isNotEmpty(productDetailDOS)) {
            throw exception(ErrorCodeConstants.CRM_PRODUCT_CATEGORY_ERROR);
        }
        List<ProductCategoryDO> productCategoryDOS = super.list(
                new LambdaQueryWrapper<ProductCategoryDO>().in(ProductCategoryDO::getPid, bizIds)
                        .eq(ProductCategoryDO::getDeleted, false));
        if (CollUtil.isNotEmpty(productCategoryDOS)) {
            throw exception(ErrorCodeConstants.CRM_PRODUCT_CATEGORY_CHILD_ERROR);
        }
        super.removeBatchByIds(entities);
    }
}
