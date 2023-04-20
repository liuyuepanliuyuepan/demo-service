package cn.klmb.crm.module.contract.service.star;

import cn.klmb.crm.framework.base.core.service.KlmbBaseServiceImpl;
import cn.klmb.crm.module.contract.dao.star.ContractStarMapper;
import cn.klmb.crm.module.contract.dto.star.ContractStarQueryDTO;
import cn.klmb.crm.module.contract.entity.star.ContractStarDO;
import org.springframework.stereotype.Service;


/**
 * 合同标星关系 Service 实现类
 *
 * @author 超级管理员
 */
@Service
public class ContractStarServiceImpl extends
        KlmbBaseServiceImpl<ContractStarDO, ContractStarQueryDTO, ContractStarMapper> implements
        ContractStarService {

    public ContractStarServiceImpl(ContractStarMapper mapper) {
        this.mapper = mapper;
    }

}
