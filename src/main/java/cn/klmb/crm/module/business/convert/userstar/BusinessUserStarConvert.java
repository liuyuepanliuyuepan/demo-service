package cn.klmb.crm.module.business.convert.userstar;

import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.module.business.controller.admin.userstar.vo.BusinessUserStarPageReqVO;
import cn.klmb.crm.module.business.controller.admin.userstar.vo.BusinessUserStarRespVO;
import cn.klmb.crm.module.business.controller.admin.userstar.vo.BusinessUserStarSaveReqVO;
import cn.klmb.crm.module.business.controller.admin.userstar.vo.BusinessUserStarUpdateReqVO;
import cn.klmb.crm.module.business.dto.userstar.BusinessUserStarQueryDTO;
import cn.klmb.crm.module.business.entity.userstar.BusinessUserStarDO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 用户商机标星关系 Convert
 *
 * @author 超级管理员
 */
@Mapper
public interface BusinessUserStarConvert {

    BusinessUserStarConvert INSTANCE = Mappers.getMapper(BusinessUserStarConvert.class);

    BusinessUserStarDO convert(BusinessUserStarSaveReqVO saveReqVO);

    BusinessUserStarDO convert(BusinessUserStarUpdateReqVO updateReqVO);

    KlmbPage<BusinessUserStarRespVO> convert(KlmbPage<BusinessUserStarDO> page);

    List<BusinessUserStarRespVO> convert(List<BusinessUserStarDO> list);

    BusinessUserStarRespVO convert(BusinessUserStarDO saveDO);

    BusinessUserStarQueryDTO convert(BusinessUserStarPageReqVO reqVO);

}
