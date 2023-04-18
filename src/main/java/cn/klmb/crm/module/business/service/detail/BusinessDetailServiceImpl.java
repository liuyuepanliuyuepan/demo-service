package cn.klmb.crm.module.business.service.detail;

import cn.klmb.crm.framework.base.core.service.KlmbBaseServiceImpl;
import cn.klmb.crm.module.business.dao.detail.BusinessDetailMapper;
import cn.klmb.crm.module.business.dto.detail.BusinessDetailQueryDTO;
import cn.klmb.crm.module.business.entity.detail.BusinessDetailDO;
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

    public BusinessDetailServiceImpl(BusinessDetailMapper mapper) {
        this.mapper = mapper;
    }

}
