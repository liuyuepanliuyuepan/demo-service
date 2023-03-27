package cn.klmb.crm.module.member.controller.admin.delivery.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 客户-收货地址更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MemberDeliveryUpdateReqVO extends MemberDeliveryBaseVO {

    @ApiModelProperty(value = "业务id", required = true, example = "249a384ea207445ca93e852d3824508c")
    @NotNull(message = "业务id不能为空")
    private String bizId;

}
