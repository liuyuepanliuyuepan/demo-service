package cn.klmb.crm.module.business.dao.detail;

import cn.klmb.crm.framework.base.core.dao.KlmbBaseMapper;
import cn.klmb.crm.module.business.dto.detail.BusinessDetailQueryDTO;
import cn.klmb.crm.module.business.entity.detail.BusinessDetailDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商机 Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface BusinessDetailMapper extends
        KlmbBaseMapper<BusinessDetailDO, BusinessDetailQueryDTO> {

}
