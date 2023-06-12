package cn.klmb.crm.module.brd.service.aliyunbill;

import cn.klmb.crm.framework.base.core.service.KlmbBaseService;
import cn.klmb.crm.module.brd.controller.admin.aliyunbill.vo.BrdBillCostVO;
import cn.klmb.crm.module.brd.controller.admin.aliyunbill.vo.BrdBillDetailRespVO;
import cn.klmb.crm.module.brd.dto.aliyunbill.BrdAliyunBillQueryDTO;
import cn.klmb.crm.module.brd.entity.aliyunbill.BrdAliyunBillDO;
import java.util.List;


/**
 * 博瑞迪阿里云账单 Service 接口
 *
 * @author 超级管理员
 */
public interface BrdAliyunBillService extends
	KlmbBaseService<BrdAliyunBillDO, BrdAliyunBillQueryDTO> {

	/**
	 * 获取账单明细
	 *
	 * @param billingCycle 账期
	 * @param billingDate 账单日期
	 * @return 账单明细
	 */
	List<BrdBillDetailRespVO> getBrdBillDetail(String billingCycle, String billingDate);

	/**
	 * 获取成本
	 *
	 * @param periodType 时间粒度
	 * @param startPeriod 开始时间
	 * @param endPeriod 结束时间
	 * @return 成本数据
	 */
	BrdBillCostVO getCostList(String periodType, String startPeriod, String endPeriod);


}
