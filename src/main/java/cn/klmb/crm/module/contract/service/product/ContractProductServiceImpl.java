package cn.klmb.crm.module.contract.service.product;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.klmb.crm.framework.base.core.service.KlmbBaseServiceImpl;
import cn.klmb.crm.module.contract.controller.admin.product.vo.ContractProductRespVO;
import cn.klmb.crm.module.contract.controller.admin.product.vo.ContractProductSaveReqVO;
import cn.klmb.crm.module.contract.convert.product.ContractProductConvert;
import cn.klmb.crm.module.contract.dao.product.ContractProductMapper;
import cn.klmb.crm.module.contract.dto.product.ContractProductQueryDTO;
import cn.klmb.crm.module.contract.entity.product.ContractProductDO;
import cn.klmb.crm.module.product.controller.admin.detail.vo.ProductDetailRespVO;
import cn.klmb.crm.module.product.service.detail.ProductDetailService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;


/**
 * 合同产品关系 Service 实现类
 *
 * @author 超级管理员
 */
@Service
public class ContractProductServiceImpl extends
        KlmbBaseServiceImpl<ContractProductDO, ContractProductQueryDTO, ContractProductMapper> implements
        ContractProductService {

    private final ProductDetailService productDetailService;

    public ContractProductServiceImpl(ContractProductMapper mapper,
            ProductDetailService productDetailService) {
        this.productDetailService = productDetailService;
        this.mapper = mapper;
    }

    @Override
    public void saveDefinition(List<ContractProductSaveReqVO> contractProductSaveReqVOList,
            String bizId) {
        contractProductSaveReqVOList.forEach(e -> {
            ContractProductDO saveDO = ContractProductConvert.INSTANCE.convert(e);
            saveDO.setContractId(bizId);
            super.saveDO(saveDO);
        });
    }

    @Override
    public void removeContractProduct(List<String> contractIds) {
        List<ContractProductDO> contractProductList = super.list(
                new LambdaQueryWrapper<ContractProductDO>().in(ContractProductDO::getContractId,
                        contractIds).eq(ContractProductDO::getDeleted, false));
        if (CollUtil.isNotEmpty(contractProductList)) {
            List<String> collect = contractProductList.stream().map(ContractProductDO::getBizId)
                    .collect(Collectors.toList());
            super.removeByBizIds(collect);
        }
    }

    @Override
    public List<ContractProductRespVO> getContractProductByContractId(String contractId) {
        List<ContractProductRespVO> convert = null;
        List<ContractProductDO> contractProductList = super.list(
                new LambdaQueryWrapper<ContractProductDO>().in(ContractProductDO::getContractId,
                        contractId).eq(ContractProductDO::getDeleted, false));
        if (CollUtil.isNotEmpty(contractProductList)) {
            convert = ContractProductConvert.INSTANCE.convert(
                    contractProductList);
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
