package cn.klmb.crm.module.member.controller.admin.delivery.vo;

import cn.klmb.crm.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 客户-收货地址分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MemberDeliveryPageReqVO extends PageParam {

    @ApiModelProperty(value = "客户ID", example = "10617")
    private String memberUserId;

    @ApiModelProperty(value = "收件人信息", example = "王五")
    private String name;

    @ApiModelProperty(value = "收件人电话")
    private String tel;

    @ApiModelProperty(value = "收货地址")
    private String address;

}
