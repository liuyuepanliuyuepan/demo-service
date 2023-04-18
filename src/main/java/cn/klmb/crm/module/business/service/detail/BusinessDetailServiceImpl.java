package cn.klmb.crm.module.business.service.detail;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.klmb.crm.framework.base.core.service.KlmbBaseServiceImpl;
import cn.klmb.crm.module.business.controller.admin.detail.vo.BusinessDetailRespVO;
import cn.klmb.crm.module.business.controller.admin.detail.vo.BusinessDetailSaveReqVO;
import cn.klmb.crm.module.business.controller.admin.detail.vo.BusinessDetailUpdateReqVO;
import cn.klmb.crm.module.business.controller.admin.product.vo.BusinessProductRespVO;
import cn.klmb.crm.module.business.controller.admin.product.vo.BusinessProductSaveReqVO;
import cn.klmb.crm.module.business.convert.detail.BusinessDetailConvert;
import cn.klmb.crm.module.business.convert.product.BusinessProductConvert;
import cn.klmb.crm.module.business.dao.detail.BusinessDetailMapper;
import cn.klmb.crm.module.business.dto.detail.BusinessDetailQueryDTO;
import cn.klmb.crm.module.business.dto.product.BusinessProductQueryDTO;
import cn.klmb.crm.module.business.entity.detail.BusinessDetailDO;
import cn.klmb.crm.module.business.entity.product.BusinessProductDO;
import cn.klmb.crm.module.business.service.product.BusinessProductService;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;


/**
 * 商机 Service 实现类
 *
 * @author 超级管理员
 */
@Service
public class BusinessDetailServiceImpl extends
        KlmbBaseServiceImpl<BusinessDetailDO, BusinessDetailQueryDTO, BusinessDetailMapper> implements
        BusinessDetailService {

    private final BusinessProductService businessProductService;

    public BusinessDetailServiceImpl(BusinessProductService businessProductService,
            BusinessDetailMapper mapper) {
        this.businessProductService = businessProductService;
        this.mapper = mapper;
    }

    @Override
    public String saveBusiness(BusinessDetailSaveReqVO saveReqVO) {
        BusinessDetailDO saveDO = BusinessDetailConvert.INSTANCE.convert(saveReqVO);
        String bizId = "";
        if (super.saveDO(saveDO)) {
            bizId = saveDO.getBizId();
        }
        //获取商机产品关系集合
        List<BusinessProductSaveReqVO> businessProductSaveList = saveReqVO.getBusinessProductSaveList();
        if (CollUtil.isNotEmpty(businessProductSaveList)) {
            businessProductSaveList.forEach(e -> {
                BusinessProductDO businessProductDO = BusinessProductConvert.INSTANCE.convert(e);
                businessProductService.saveDO(businessProductDO);
            });
        }
        return bizId;
    }

    @Override
    public void removeByBizIds(List<String> bizIds) {
        if (CollUtil.isEmpty(bizIds)) {
            return;
        }
        List<BusinessDetailDO> entities = this.listByBizIds(bizIds);
        if (CollUtil.isEmpty(entities)) {
            return;
        }
        super.removeBatchByIds(entities);
        //同时删除商机产品关系集合
        businessProductService.removeBusinessProduct(bizIds);
    }

    @Override
    public boolean updateBusiness(BusinessDetailUpdateReqVO updateReqVO) {
        BusinessDetailDO updateDO = BusinessDetailConvert.INSTANCE.convert(updateReqVO);
        boolean success = super.updateDO(updateDO);
        if (success) {
            //删除历史商机产品关系集合
            businessProductService.removeBusinessProduct(
                    Collections.singletonList(updateReqVO.getBizId()));
            //获取商机产品关系集合
            List<BusinessProductSaveReqVO> businessProductSaveList = updateReqVO.getBusinessProductSaveList();
            if (CollUtil.isNotEmpty(businessProductSaveList)) {
                businessProductSaveList.forEach(e -> {
                    BusinessProductDO businessProductDO = BusinessProductConvert.INSTANCE.convert(
                            e);
                    businessProductService.saveDO(businessProductDO);
                });
            }
        }
        return success;
    }

    @Override
    public BusinessDetailRespVO getBusinessByBizId(String bizId) {
        BusinessDetailDO businessDetailDO = super.getByBizId(bizId);
        BusinessDetailRespVO respVO = BusinessDetailConvert.INSTANCE.convert(businessDetailDO);
        List<BusinessProductRespVO> productRespList = Collections.emptyList();
        if (ObjectUtil.isNotNull(respVO)) {
            List<BusinessProductDO> businessProductList = businessProductService.list(
                    BusinessProductQueryDTO.builder().businessId(bizId).build());
            if (CollUtil.isNotEmpty(businessProductList)) {
                productRespList = BusinessProductConvert.INSTANCE.convert(
                        businessProductList);
            }
        }
        respVO.setBusinessProductRespList(productRespList);
        return respVO;
    }


}
