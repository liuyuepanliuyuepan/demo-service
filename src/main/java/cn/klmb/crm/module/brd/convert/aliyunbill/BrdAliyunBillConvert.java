package cn.klmb.crm.module.brd.convert.aliyunbill;

import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.module.brd.controller.admin.aliyunbill.vo.*;
import cn.klmb.crm.module.brd.dto.aliyunbill.BrdAliyunBillQueryDTO;
import cn.klmb.crm.module.brd.entity.aliyunbill.BrdAliyunBillDO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 博瑞迪阿里云账单 Convert
 *
 * @author 超级管理员
 */
@Mapper
public interface BrdAliyunBillConvert {

	BrdAliyunBillConvert INSTANCE = Mappers.getMapper(BrdAliyunBillConvert.class);

	BrdAliyunBillDO convert(BrdAliyunBillSaveReqVO saveReqVO);

	BrdAliyunBillDO convert(BrdAliyunBillUpdateReqVO updateReqVO);

	KlmbPage<BrdAliyunBillRespVO> convert(KlmbPage<BrdAliyunBillDO> page);

	List<BrdAliyunBillRespVO> convert(List<BrdAliyunBillDO> list);

	BrdAliyunBillRespVO convert(BrdAliyunBillDO saveDO);

	BrdAliyunBillQueryDTO convert(BrdAliyunBillPageReqVO reqVO);

}
