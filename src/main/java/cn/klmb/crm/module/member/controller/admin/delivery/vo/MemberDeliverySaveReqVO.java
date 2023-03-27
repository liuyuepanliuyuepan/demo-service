package cn.klmb.crm.module.member.controller.admin.delivery.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 客户-收货地址创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MemberDeliverySaveReqVO extends MemberDeliveryBaseVO {

    @ApiModelProperty(value = "客户ID", required = true, example = "10617")
    @NotNull(message = "客户ID不能为空")
    private String memberUserId;

}
