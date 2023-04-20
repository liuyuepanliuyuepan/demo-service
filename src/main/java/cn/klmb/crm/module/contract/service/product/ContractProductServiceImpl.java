package cn.klmb.crm.module.contract.service.product;

import cn.klmb.crm.framework.base.core.service.KlmbBaseServiceImpl;
import cn.klmb.crm.module.contract.controller.admin.product.vo.ContractProductSaveReqVO;
import cn.klmb.crm.module.contract.convert.detail.ContractDetailConvert;
import cn.klmb.crm.module.contract.convert.product.ContractProductConvert;
import cn.klmb.crm.module.contract.convert.product.ContractProductConvertImpl;
import cn.klmb.crm.module.contract.dao.product.ContractProductMapper;
import cn.klmb.crm.module.contract.dto.product.ContractProductQueryDTO;
import cn.klmb.crm.module.contract.entity.detail.ContractDetailDO;
import cn.klmb.crm.module.contract.entity.product.ContractProductDO;
import java.util.List;
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

    public ContractProductServiceImpl(ContractProductMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void saveDefinition(List<ContractProductSaveReqVO> contractProductSaveReqVOList,
            String bizId) {
        contractProductSaveReqVOList.forEach(e->{
            ContractProductDO saveDO = ContractProductConvert.INSTANCE.convert(e);
            saveDO.setContractId(bizId);
            super.saveDO(saveDO);
        });
    }
}
