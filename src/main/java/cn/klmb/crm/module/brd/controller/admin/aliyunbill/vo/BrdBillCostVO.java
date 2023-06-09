package cn.klmb.crm.module.brd.controller.admin.aliyunbill.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.List;
import lombok.Builder.Default;
import lombok.Data;
import lombok.ToString;

/**
 * 描述：
 *
 * @author: 蒋雷佳
 * @date: 2023/6/8 16:16
 */
@Data
@ToString
@ApiModel("合计统计数据")
public class BrdBillCostVO {

	@ApiModelProperty(value = "总成本")
	private CostTotals costTotals;

	@ApiModelProperty(value = "账单日期产品明细")
	private List<CostItem> costItems;

}
