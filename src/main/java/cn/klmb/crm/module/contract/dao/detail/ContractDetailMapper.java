package cn.klmb.crm.module.contract.dao.detail;

import cn.klmb.crm.framework.base.core.dao.KlmbBaseMapper;
import cn.klmb.crm.module.contract.dto.detail.ContractDetailQueryDTO;
import cn.klmb.crm.module.contract.entity.detail.ContractDetailDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 合同详情 Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface ContractDetailMapper extends KlmbBaseMapper<ContractDetailDO, ContractDetailQueryDTO> {

}
