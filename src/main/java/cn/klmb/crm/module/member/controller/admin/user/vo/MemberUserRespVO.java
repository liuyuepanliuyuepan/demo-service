package cn.klmb.crm.module.member.controller.admin.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 客户-用户 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MemberUserRespVO extends MemberUserBaseVO {

    @ApiModelProperty(value = "业务id", required = true, example = "7943b068b7754f0fa8ec7f9ea7cf949e")
    private String bizId;

    @ApiModelProperty(value = "创建时间", required = true)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "订单数量", required = true)
    private Integer orderCount;

    @ApiModelProperty(value = "累计成交额（单位：分）", required = true)
    private Long tradeAmount;

}
