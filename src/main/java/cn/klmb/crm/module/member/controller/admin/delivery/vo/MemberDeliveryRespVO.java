package cn.klmb.crm.module.member.controller.admin.delivery.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 客户-收货地址 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MemberDeliveryRespVO extends MemberDeliveryBaseVO {

    @ApiModelProperty(value = "业务id", required = true, example = "249a384ea207445ca93e852d3824508c")
    private String bizId;

    @ApiModelProperty(value = "创建时间", required = true)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "客户ID", required = true, example = "10617")
    private String memberUserId;

}
