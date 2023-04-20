package cn.klmb.crm.module.member.controller.admin.contacts.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 联系人创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MemberContactsSaveReqVO extends MemberContactsBaseVO {

    /**
     * 商机id
     */
    @ApiModelProperty(value = "商机id")
    private String businessId;

}
