package cn.klmb.crm.module.contract.service.star;

import cn.klmb.crm.framework.base.core.service.KlmbBaseService;
import cn.klmb.crm.module.contract.dto.star.ContractStarQueryDTO;
import cn.klmb.crm.module.contract.entity.star.ContractStarDO;


/**
 * 合同标星关系 Service 接口
 *
 * @author 超级管理员
 */
public interface ContractStarService extends KlmbBaseService<ContractStarDO, ContractStarQueryDTO> {

    /**
     * 取消关注
     * @param contractId 合同id
     */
    void cancelStar(String contractId);
}
