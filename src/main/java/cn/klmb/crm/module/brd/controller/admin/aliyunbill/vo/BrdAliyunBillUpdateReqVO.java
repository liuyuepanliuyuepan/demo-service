package cn.klmb.crm.module.brd.controller.admin.aliyunbill.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;
import javax.validation.constraints.*;

@ApiModel("管理后台 - 博瑞迪阿里云账单更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BrdAliyunBillUpdateReqVO extends BrdAliyunBillBaseVO {

	@ApiModelProperty(value = "业务id", required = true, example = "ac08ca6a84a14933afd764ccf48058ec")
	@NotNull(message = "业务id不能为空")
	private String bizId;

}
