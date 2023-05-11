package cn.klmb.crm.module.business.convert.detail;

import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.framework.base.core.pojo.KlmbScrollPage;
import cn.klmb.crm.module.business.controller.admin.detail.vo.BusinessDetailPageReqVO;
import cn.klmb.crm.module.business.controller.admin.detail.vo.BusinessDetailRespVO;
import cn.klmb.crm.module.business.controller.admin.detail.vo.BusinessDetailSaveReqVO;
import cn.klmb.crm.module.business.controller.admin.detail.vo.BusinessDetailScrollPageReqVO;
import cn.klmb.crm.module.business.controller.admin.detail.vo.BusinessDetailUpdateReqVO;
import cn.klmb.crm.module.business.dto.detail.BusinessDetailQueryDTO;
import cn.klmb.crm.module.business.entity.detail.BusinessDetailDO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 商机 Convert
 *
 * @author 超级管理员
 */
@Mapper
public interface BusinessDetailConvert {

    BusinessDetailConvert INSTANCE = Mappers.getMapper(BusinessDetailConvert.class);

    BusinessDetailDO convert(BusinessDetailSaveReqVO saveReqVO);

    BusinessDetailDO convert(BusinessDetailUpdateReqVO updateReqVO);

    KlmbPage<BusinessDetailRespVO> convert(KlmbPage<BusinessDetailDO> page);

    List<BusinessDetailRespVO> convert(List<BusinessDetailDO> list);

    BusinessDetailRespVO convert(BusinessDetailDO saveDO);

    BusinessDetailQueryDTO convert(BusinessDetailPageReqVO reqVO);

    BusinessDetailQueryDTO convert(BusinessDetailScrollPageReqVO bean);


    KlmbScrollPage<BusinessDetailRespVO> convert(KlmbScrollPage<BusinessDetailDO> page);


}
