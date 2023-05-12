package cn.klmb.crm.module.contract.convert.star;

import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.module.contract.controller.admin.star.vo.*;
import cn.klmb.crm.module.contract.dto.star.ContractStarQueryDTO;
import cn.klmb.crm.module.contract.entity.star.ContractStarDO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 合同标星关系 Convert
 *
 * @author 超级管理员
 */
@Mapper
public interface ContractStarConvert {

    ContractStarConvert INSTANCE = Mappers.getMapper(ContractStarConvert.class);

    ContractStarDO convert(ContractStarSaveReqVO saveReqVO);

    ContractStarDO convert(ContractStarUpdateReqVO updateReqVO);

    KlmbPage<ContractStarRespVO> convert(KlmbPage<ContractStarDO> page);

    List<ContractStarRespVO> convert(List<ContractStarDO> list);

    ContractStarRespVO convert(ContractStarDO saveDO);

    ContractStarQueryDTO convert(ContractStarPageReqVO reqVO);

}
