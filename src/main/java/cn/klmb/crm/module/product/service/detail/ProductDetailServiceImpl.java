package cn.klmb.crm.module.product.service.detail;

import static cn.klmb.crm.framework.common.exception.util.ServiceExceptionUtil.exception;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.framework.base.core.service.KlmbBaseServiceImpl;
import cn.klmb.crm.framework.web.core.util.WebFrameworkUtils;
import cn.klmb.crm.module.product.controller.admin.detail.vo.ProductDetailPageReqVO;
import cn.klmb.crm.module.product.controller.admin.detail.vo.ProductDetailRespVO;
import cn.klmb.crm.module.product.convert.detail.ProductDetailConvert;
import cn.klmb.crm.module.product.dao.detail.ProductDetailMapper;
import cn.klmb.crm.module.product.dto.detail.ProductDetailQueryDTO;
import cn.klmb.crm.module.product.entity.category.ProductCategoryDO;
import cn.klmb.crm.module.product.entity.detail.ProductDetailDO;
import cn.klmb.crm.module.product.entity.userstar.ProductUserStarDO;
import cn.klmb.crm.module.product.enums.ShelfStatusEnum;
import cn.klmb.crm.module.product.service.category.ProductCategoryService;
import cn.klmb.crm.module.product.service.userstar.ProductUserStarService;
import cn.klmb.crm.module.system.entity.user.SysUserDO;
import cn.klmb.crm.module.system.enums.CrmSceneEnum;
import cn.klmb.crm.module.system.enums.ErrorCodeConstants;
import cn.klmb.crm.module.system.service.user.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;


/**
 * 产品 Service 实现类
 *
 * @author 超级管理员
 */
@Service
public class ProductDetailServiceImpl extends
        KlmbBaseServiceImpl<ProductDetailDO, ProductDetailQueryDTO, ProductDetailMapper> implements
        ProductDetailService {

    private final ProductUserStarService productUserStarService;

    private final SysUserService sysUserService;

    private final ProductCategoryService productCategoryService;

    public ProductDetailServiceImpl(ProductUserStarService productUserStarService,
            ProductDetailMapper mapper, SysUserService sysUserService,
            @Lazy ProductCategoryService productCategoryService) {
        this.productUserStarService = productUserStarService;
        this.sysUserService = sysUserService;
        this.productCategoryService = productCategoryService;
        this.mapper = mapper;
    }


    @Override
    public void star(String bizId) {
        String userId = WebFrameworkUtils.getLoginUserId();
        if (StrUtil.isBlank(userId)) {
            throw exception(ErrorCodeConstants.USER_NOT_EXISTS);
        }
        LambdaQueryWrapper<ProductUserStarDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProductUserStarDO::getProductId, bizId);
        wrapper.eq(ProductUserStarDO::getUserId, userId);
        wrapper.eq(ProductUserStarDO::getDeleted, false);
        ProductUserStarDO star = productUserStarService.getOne(wrapper);
        if (star == null) {
            star = new ProductUserStarDO();
            star.setProductId(bizId);
            star.setUserId(userId);
            productUserStarService.save(star);
        } else {
            productUserStarService.removeById(star.getId());
        }
    }

    @Override
    public KlmbPage<ProductDetailRespVO> page(ProductDetailPageReqVO reqVO) {
        //获取当前用户id
        String userId = WebFrameworkUtils.getLoginUserId();
        if (StrUtil.isBlank(userId)) {
            throw exception(ErrorCodeConstants.USER_NOT_EXISTS);
        }

        KlmbPage<ProductDetailDO> klmbPage = KlmbPage.<ProductDetailDO>builder()
                .pageNo(reqVO.getPageNo())
                .pageSize(reqVO.getPageSize())
                .build();

        ProductDetailQueryDTO queryDTO = ProductDetailConvert.INSTANCE.convert(reqVO);
        //全部产品
        if (ObjectUtil.equals(reqVO.getSceneId(), CrmSceneEnum.ALL.getType())) {
            klmbPage = super.page(queryDTO, klmbPage);
        }

        if (ObjectUtil.equals(reqVO.getSceneId(), CrmSceneEnum.ON_SHELF.getType())) {
            queryDTO.setStatus(ShelfStatusEnum.ON_SHELF.getValue());
            klmbPage = super.page(queryDTO, klmbPage);
        }

        if (ObjectUtil.equals(reqVO.getSceneId(), CrmSceneEnum.UNDER_SHELF.getType())) {
            queryDTO.setStatus(ShelfStatusEnum.UNDER_SHELF.getValue());
            klmbPage = super.page(queryDTO, klmbPage);
        }

        if (ObjectUtil.equals(reqVO.getSceneId(), CrmSceneEnum.STAR.getType())) {
            List<ProductUserStarDO> productUserStarDOS = productUserStarService.list(
                    new LambdaQueryWrapper<ProductUserStarDO>().eq(ProductUserStarDO::getUserId,
                            userId).eq(ProductUserStarDO::getDeleted, false));
            if (CollUtil.isNotEmpty(productUserStarDOS)) {
                List<String> collect = productUserStarDOS.stream()
                        .map(ProductUserStarDO::getProductId).collect(Collectors.toList());
                queryDTO.setBizIds(collect);
                klmbPage = super.page(queryDTO, klmbPage);
            }
        }

        List<ProductDetailDO> content = klmbPage.getContent();
        if (CollUtil.isNotEmpty(content)) {
            content.forEach(e -> {
                SysUserDO sysUserDO = sysUserService.getByBizId(e.getOwnerUserId());
                if (ObjectUtil.isNotNull(sysUserDO)) {
                    e.setOwnerUserName(sysUserDO.getNickname());
                }

                List<ProductUserStarDO> productUserStarDOS = productUserStarService.list(
                        new LambdaQueryWrapper<ProductUserStarDO>().eq(
                                        ProductUserStarDO::getProductId, e.getBizId())
                                .eq(ProductUserStarDO::getUserId, userId)
                                .eq(ProductUserStarDO::getDeleted, false));
                e.setStar(CollUtil.isNotEmpty(productUserStarDOS));

                if (StrUtil.isNotBlank(e.getCategoryId())) {
                    ProductCategoryDO productCategoryDO = productCategoryService.getByBizId(
                            e.getCategoryId());
                    e.setCategoryName(
                            ObjectUtil.isNotNull(productCategoryDO) ? productCategoryDO.getName()
                                    : null);
                }
            });
        }
        return ProductDetailConvert.INSTANCE.convert(klmbPage);
    }

}
