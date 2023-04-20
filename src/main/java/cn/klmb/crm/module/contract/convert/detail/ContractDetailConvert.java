package cn.klmb.crm.module.contract.convert.detail;

import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.module.contract.controller.admin.detail.vo.*;
import cn.klmb.crm.module.contract.dto.detail.ContractDetailQueryDTO;
import cn.klmb.crm.module.contract.entity.detail.ContractDetailDO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 合同详情 Convert
 *
 * @author 超级管理员
 */
@Mapper
public interface ContractDetailConvert {

    ContractDetailConvert INSTANCE = Mappers.getMapper(ContractDetailConvert.class);

    ContractDetailDO convert(ContractDetailSaveReqVO saveReqVO);

    ContractDetailDO convert(ContractDetailUpdateReqVO updateReqVO);

    KlmbPage<ContractDetailRespVO> convert(KlmbPage<ContractDetailDO> page);

    List<ContractDetailRespVO> convert(List<ContractDetailDO> list);

    ContractDetailRespVO convert(ContractDetailDO saveDO);

    ContractDetailQueryDTO convert(ContractDetailPageReqVO reqVO);

}
