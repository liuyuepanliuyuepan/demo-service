package cn.klmb.crm.module.business.dao.userstar;

import cn.klmb.crm.framework.base.core.dao.KlmbBaseMapper;
import cn.klmb.crm.module.business.dto.userstar.BusinessUserStarQueryDTO;
import cn.klmb.crm.module.business.entity.userstar.BusinessUserStarDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户商机标星关系 Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface BusinessUserStarMapper extends
        KlmbBaseMapper<BusinessUserStarDO, BusinessUserStarQueryDTO> {

}
