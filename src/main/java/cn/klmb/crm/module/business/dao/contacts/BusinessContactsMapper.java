package cn.klmb.crm.module.business.dao.contacts;

import cn.klmb.crm.framework.base.core.dao.KlmbBaseMapper;
import cn.klmb.crm.module.business.dto.contacts.BusinessContactsQueryDTO;
import cn.klmb.crm.module.business.entity.contacts.BusinessContactsDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商机联系人关联 Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface BusinessContactsMapper extends
        KlmbBaseMapper<BusinessContactsDO, BusinessContactsQueryDTO> {

}
