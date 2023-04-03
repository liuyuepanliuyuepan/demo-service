package cn.klmb.crm.module.member.controller.admin.contacts.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@ApiModel("管理后台 - 首要联系人 Request VO")
@Data
@ToString(callSuper = true)
public class MemberFirstContactsReqVO {

    @ApiModelProperty("联系人ID")
    private String contactsId;

    @ApiModelProperty("客户ID")
    private String customerId;

    @ApiModelProperty("商机ID(暂时不用传)")
    private Integer businessId;


}
