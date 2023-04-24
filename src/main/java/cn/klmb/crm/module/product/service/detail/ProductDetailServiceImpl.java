package cn.klmb.crm.module.product.service.detail;

import static cn.klmb.crm.framework.common.exception.util.ServiceExceptionUtil.exception;

import cn.hutool.core.util.StrUtil;
import cn.klmb.crm.framework.base.core.service.KlmbBaseServiceImpl;
import cn.klmb.crm.framework.web.core.util.WebFrameworkUtils;
import cn.klmb.crm.module.product.dao.detail.ProductDetailMapper;
import cn.klmb.crm.module.product.dto.detail.ProductDetailQueryDTO;
import cn.klmb.crm.module.product.entity.detail.ProductDetailDO;
import cn.klmb.crm.module.product.entity.userstar.ProductUserStarDO;
import cn.klmb.crm.module.product.service.userstar.ProductUserStarService;
import cn.klmb.crm.module.system.enums.ErrorCodeConstants;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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

    public ProductDetailServiceImpl(ProductUserStarService productUserStarService,
            ProductDetailMapper mapper) {
        this.productUserStarService = productUserStarService;
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
}
