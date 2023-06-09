package cn.klmb.crm.module.brd.controller.admin.aliyunbill.vo;

import lombok.*;
import java.time.LocalDateTime;
import io.swagger.annotations.*;

@ApiModel("管理后台 - 博瑞迪阿里云账单 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BrdAliyunBillRespVO extends BrdAliyunBillBaseVO {

	@ApiModelProperty(value = "业务id", required = true, example = "ac08ca6a84a14933afd764ccf48058ec")
	private String bizId;

	@ApiModelProperty(value = "创建时间", required = true)
	private LocalDateTime createTime;

}
