package cn.klmb.crm.module.business.service.userstar;

import cn.klmb.crm.framework.base.core.service.KlmbBaseServiceImpl;
import cn.klmb.crm.module.business.dao.userstar.BusinessUserStarMapper;
import cn.klmb.crm.module.business.dto.userstar.BusinessUserStarQueryDTO;
import cn.klmb.crm.module.business.entity.userstar.BusinessUserStarDO;
import org.springframework.stereotype.Service;


/**
 * 用户商机标星关系 Service 实现类
 *
 * @author 超级管理员
 */
@Service
public class BusinessUserStarServiceImpl extends
        KlmbBaseServiceImpl<BusinessUserStarDO, BusinessUserStarQueryDTO, BusinessUserStarMapper> implements
        BusinessUserStarService {

    public BusinessUserStarServiceImpl(BusinessUserStarMapper mapper) {
        this.mapper = mapper;
    }

}
