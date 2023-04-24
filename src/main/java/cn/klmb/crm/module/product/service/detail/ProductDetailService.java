package cn.klmb.crm.module.product.service.detail;

import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.framework.base.core.service.KlmbBaseService;
import cn.klmb.crm.module.product.controller.admin.detail.vo.ProductDetailPageReqVO;
import cn.klmb.crm.module.product.controller.admin.detail.vo.ProductDetailRespVO;
import cn.klmb.crm.module.product.dto.detail.ProductDetailQueryDTO;
import cn.klmb.crm.module.product.entity.detail.ProductDetailDO;


/**
 * 产品 Service 接口
 *
 * @author 超级管理员
 */
public interface ProductDetailService extends
        KlmbBaseService<ProductDetailDO, ProductDetailQueryDTO> {


    /**
     * 产品标星
     *
     * @param bizId
     */
    void star(String bizId);


    /**
     * 产品分页列表
     *
     * @param reqVO 查询条件
     * @return 表单分页列表
     */
    KlmbPage<ProductDetailRespVO> page(ProductDetailPageReqVO reqVO);
}
