package cn.klmb.crm.module.business.service.contacts;

import cn.klmb.crm.framework.base.core.service.KlmbBaseServiceImpl;
import cn.klmb.crm.module.business.dao.contacts.BusinessContactsMapper;
import cn.klmb.crm.module.business.dto.contacts.BusinessContactsQueryDTO;
import cn.klmb.crm.module.business.entity.contacts.BusinessContactsDO;
import org.springframework.stereotype.Service;


/**
 * 商机联系人关联 Service 实现类
 *
 * @author 超级管理员
 */
@Service
public class BusinessContactsServiceImpl extends
        KlmbBaseServiceImpl<BusinessContactsDO, BusinessContactsQueryDTO, BusinessContactsMapper> implements
        BusinessContactsService {

    public BusinessContactsServiceImpl(BusinessContactsMapper mapper) {
        this.mapper = mapper;
    }

}
