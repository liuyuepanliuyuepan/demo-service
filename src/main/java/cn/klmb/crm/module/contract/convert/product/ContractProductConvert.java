package cn.klmb.crm.module.contract.convert.product;

import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.module.contract.controller.admin.product.vo.*;
import cn.klmb.crm.module.contract.dto.product.ContractProductQueryDTO;
import cn.klmb.crm.module.contract.entity.product.ContractProductDO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 合同产品关系 Convert
 *
 * @author 超级管理员
 */
@Mapper
public interface ContractProductConvert {

    ContractProductConvert INSTANCE = Mappers.getMapper(ContractProductConvert.class);

    ContractProductDO convert(ContractProductSaveReqVO saveReqVO);

    ContractProductDO convert(ContractProductUpdateReqVO updateReqVO);

    KlmbPage<ContractProductRespVO> convert(KlmbPage<ContractProductDO> page);

    List<ContractProductRespVO> convert(List<ContractProductDO> list);

    ContractProductRespVO convert(ContractProductDO saveDO);

    ContractProductQueryDTO convert(ContractProductPageReqVO reqVO);

}
