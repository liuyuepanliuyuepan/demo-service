package cn.klmb.crm.module.brd.controller.admin.aliyunbill.vo;

import cn.klmb.crm.framework.jackson.core.databind.BigDecimalFormatSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
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
@ApiModel("合计明细")
public class CostItemDetail {


	@ApiModelProperty(value = "总成本")
	private BigDecimal amount;

	@ApiModelProperty(value = "当前产品金额占全部产品金额百分比")
	private BigDecimal percentage;

	@ApiModelProperty(value = "产品编码")
	private String code;

	@ApiModelProperty(value = "产品名称")
	private String title;


}
