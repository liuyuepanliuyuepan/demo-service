package cn.klmb.crm.module.contract.dao.product;

import cn.klmb.crm.framework.base.core.dao.KlmbBaseMapper;
import cn.klmb.crm.module.contract.dto.product.ContractProductQueryDTO;
import cn.klmb.crm.module.contract.entity.product.ContractProductDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 合同产品关系 Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface ContractProductMapper extends KlmbBaseMapper<ContractProductDO, ContractProductQueryDTO> {

}
