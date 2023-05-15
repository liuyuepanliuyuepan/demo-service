package cn.klmb.crm.module.contract.service.product;

import cn.klmb.crm.framework.base.core.service.KlmbBaseService;
import cn.klmb.crm.module.contract.controller.admin.product.vo.ContractProductSaveReqVO;
import cn.klmb.crm.module.contract.dto.product.ContractProductQueryDTO;
import cn.klmb.crm.module.contract.entity.product.ContractProductDO;
import java.util.List;


/**
 * 合同产品关系 Service 接口
 *
 * @author 超级管理员
 */
public interface ContractProductService extends
        KlmbBaseService<ContractProductDO, ContractProductQueryDTO> {

    /**
     * 保存合同与产品的关系
     *
     * @param contractProductSaveReqVOList 产品列表
     * @param bizId                        合同id
     */
    void saveDefinition(List<ContractProductSaveReqVO> contractProductSaveReqVOList, String bizId);


    /**
     * 根据contractIds逻辑删除其商机产品关系
     *
     * @param contractIds
     */
    void removeContractProduct(List<String> contractIds);
}
