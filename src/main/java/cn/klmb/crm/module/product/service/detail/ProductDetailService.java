package cn.klmb.crm.module.product.service.detail;

import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.framework.base.core.pojo.KlmbScrollPage;
import cn.klmb.crm.framework.base.core.service.KlmbBaseService;
import cn.klmb.crm.module.member.controller.admin.user.vo.CrmChangeOwnerUserBO;
import cn.klmb.crm.module.product.controller.admin.detail.vo.ProductDetailPageReqVO;
import cn.klmb.crm.module.product.controller.admin.detail.vo.ProductDetailRespVO;
import cn.klmb.crm.module.product.controller.admin.detail.vo.ProductDetailScrollPageReqVO;
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


    /**
     * 根据业务id查询产品详细信息
     *
     * @param bizId 业务id
     * @return 实体
     */
    ProductDetailRespVO getProductDetailByBizId(String bizId);


    /**
     * 产品滚动分页
     *
     * @param reqVO
     * @return
     */
    KlmbScrollPage<ProductDetailRespVO> pageScroll(ProductDetailScrollPageReqVO reqVO);


    /**
     * 修改产品负责人
     *
     * @param changOwnerUserBO data
     */
    void changeOwnerUser(CrmChangeOwnerUserBO changOwnerUserBO);
}
