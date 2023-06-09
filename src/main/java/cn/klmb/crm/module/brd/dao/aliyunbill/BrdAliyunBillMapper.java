package cn.klmb.crm.module.brd.dao.aliyunbill;

import cn.klmb.crm.framework.base.core.dao.KlmbBaseMapper;
import cn.klmb.crm.module.brd.dto.aliyunbill.BrdAliyunBillQueryDTO;
import cn.klmb.crm.module.brd.entity.aliyunbill.BrdAliyunBillDO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 博瑞迪阿里云账单 Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface BrdAliyunBillMapper extends
	KlmbBaseMapper<BrdAliyunBillDO, BrdAliyunBillQueryDTO> {

	/**
	 * 获取指定账单日期区间内的账单
	 *
	 * @return 账单结果
	 */
	List<BrdAliyunBillDO> getBrdAliyunBill(@Param("startPeriod") String startPeriod,
		@Param("endPeriod") String endPeriod);
}
