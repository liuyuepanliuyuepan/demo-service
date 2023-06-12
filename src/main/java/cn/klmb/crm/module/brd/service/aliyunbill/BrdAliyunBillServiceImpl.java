package cn.klmb.crm.module.brd.service.aliyunbill;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.klmb.crm.framework.base.core.service.KlmbBaseServiceImpl;
import cn.klmb.crm.module.brd.controller.admin.aliyunbill.vo.BrdBillCostVO;
import cn.klmb.crm.module.brd.controller.admin.aliyunbill.vo.BrdBillDetailRespVO;
import cn.klmb.crm.module.brd.controller.admin.aliyunbill.vo.CostItem;
import cn.klmb.crm.module.brd.controller.admin.aliyunbill.vo.CostItemDetail;
import cn.klmb.crm.module.brd.controller.admin.aliyunbill.vo.CostTotals;
import cn.klmb.crm.module.brd.dao.aliyunbill.BrdAliyunBillMapper;
import cn.klmb.crm.module.brd.dto.aliyunbill.BrdAliyunBillQueryDTO;
import cn.klmb.crm.module.brd.entity.aliyunbill.BrdAliyunBillDO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


/**
 * 博瑞迪阿里云账单 Service 实现类
 *
 * @author 超级管理员
 */
@Service
public class BrdAliyunBillServiceImpl extends
	KlmbBaseServiceImpl<BrdAliyunBillDO, BrdAliyunBillQueryDTO, BrdAliyunBillMapper> implements
	BrdAliyunBillService {

	public BrdAliyunBillServiceImpl(BrdAliyunBillMapper mapper) {
		this.mapper = mapper;
	}

	public static void main(String[] args) {
		System.out.println(new BigDecimal("0.1").setScale(6));
	}

	@Override
	public List<BrdBillDetailRespVO> getBrdBillDetail(String billingCycle, String billingDate) {
		List<BrdBillDetailRespVO> detailList = new ArrayList<BrdBillDetailRespVO>();
		LambdaQueryWrapper<BrdAliyunBillDO> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(BrdAliyunBillDO::getBillingCycle, billingCycle);
		wrapper.eq(BrdAliyunBillDO::getBillingDate, billingDate);
		List<BrdAliyunBillDO> list = super.list(wrapper);
		BrdBillDetailRespVO brdBillDetailRespVO;
		for (BrdAliyunBillDO brdAliyunBillDO : list) {
			brdBillDetailRespVO = new BrdBillDetailRespVO();
			BeanUtils.copyProperties(brdAliyunBillDO, brdBillDetailRespVO);
			detailList.add(brdBillDetailRespVO);
		}
		return detailList;
	}

	@Override
	public BrdBillCostVO getCostList(String periodType, String startPeriod, String endPeriod) {

		BrdBillCostVO brdBillCostVO = new BrdBillCostVO();
		if (periodType.equals("MONTH")) {
			Date startDate = DateUtil.parse(startPeriod.substring(0, 7) + "-01");
			startPeriod = DateUtil.beginOfMonth(startDate).toString("yyyy-MM-dd");
			Date endDate = DateUtil.parse(endPeriod.substring(0, 7) + "-01");
			endPeriod = DateUtil.endOfMonth(endDate).toString("yyyy-MM-dd");
		}
		List<BrdAliyunBillDO> list = mapper.getBrdAliyunBill(startPeriod, endPeriod);
		if (!list.isEmpty()) {
			CostTotals costTotals = new CostTotals();
			costTotals.setAmount(new BigDecimal("0.00"));
			Map<String, List<CostItemDetail>> periodMap = new LinkedHashMap();
			CostItemDetail itemDetail;
			for (BrdAliyunBillDO billDO : list) {
				itemDetail = new CostItemDetail();
				itemDetail.setAmount(billDO.getPaymentAmount());
				itemDetail.setCode(billDO.getProductCode());
				itemDetail.setTitle(billDO.getProductName());
				String period = periodType.equals("MONTH") ? billDO.getBillingDate().substring(0, 7)
					: billDO.getBillingDate();
				if (periodMap.containsKey(period)) {
					periodMap.get(period).add(itemDetail);
				} else {
					List<CostItemDetail> perList = new ArrayList<>();
					perList.add(itemDetail);
					periodMap.put(period, perList);
				}
			}

			List<CostItem> costItems = new ArrayList<>();
			periodMap.forEach((key, value) -> {
				CostItem costItem = new CostItem();
				costItem.setAmount(new BigDecimal("0.00"));
				costItem.setTimePeriod(key);
				Map<String, CostItemDetail> productMap = new HashMap();
				List<CostItemDetail> costItemDetails = new ArrayList<>();
				// 按时间粒度汇总成本
				value.forEach(costItemDetail -> {
					costItem.setAmount(
						costItem.getAmount().add(costItemDetail.getAmount()).setScale(2));
					// 按照产品进行汇总
					if (productMap.containsKey(costItemDetail.getCode())) {
						BigDecimal currentAmount = productMap.get(costItemDetail.getCode())
							.getAmount();
						productMap.get(costItemDetail.getCode())
							.setAmount(currentAmount.add(costItemDetail.getAmount()).setScale(2));
					} else {
						productMap.put(costItemDetail.getCode(), costItemDetail);
					}
				});
				//计算占比
				productMap.forEach((productCode, productItem) -> {
					productItem.setPercentage(
						NumberUtil.div(productItem.getAmount(), costItem.getAmount(), 2,
							RoundingMode.HALF_UP));
					costItemDetails.add(productItem);
				});
				costItem.setCostItemDetails(costItemDetails);
				costItems.add(costItem);
				costTotals.setAmount(NumberUtil.add(costTotals.getAmount(), costItem.getAmount()));
			});
			brdBillCostVO.setCostItems(costItems);
			brdBillCostVO.setCostTotals(costTotals);
		}
		return brdBillCostVO;
	}
}
