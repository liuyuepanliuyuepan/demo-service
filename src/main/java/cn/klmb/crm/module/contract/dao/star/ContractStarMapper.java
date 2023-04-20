package cn.klmb.crm.module.contract.dao.star;

import cn.klmb.crm.framework.base.core.dao.KlmbBaseMapper;
import cn.klmb.crm.module.contract.dto.star.ContractStarQueryDTO;
import cn.klmb.crm.module.contract.entity.star.ContractStarDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 合同标星关系 Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface ContractStarMapper extends KlmbBaseMapper<ContractStarDO, ContractStarQueryDTO> {

}
