package cn.klmb.crm.module.brd.controller.admin.aliyunbill.vo;

import cn.klmb.crm.framework.jackson.core.databind.BigDecimalFormatSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import lombok.Builder;
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
@ApiModel("合计成本")
public class CostTotals {


	@ApiModelProperty(value = "总成本")
	private BigDecimal amount;

	@ApiModelProperty(value = "币种，取值：•	CNY。•	USD。•	JPY。 ")
	private String currency = "CNY";

}
