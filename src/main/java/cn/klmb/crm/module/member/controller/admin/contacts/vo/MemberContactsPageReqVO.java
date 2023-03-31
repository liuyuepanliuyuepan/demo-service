package cn.klmb.crm.module.member.controller.admin.contacts.vo;

import cn.klmb.crm.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 联系人分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MemberContactsPageReqVO extends PageParam {

    /**
     * 关键字查询
     */
    private String keyword;

    @ApiModelProperty(value = "场景ID(1全部,2自己负责的,3下属负责的,4关注的,默认传1)")
    private Integer sceneId;

    /**
     * 客户id
     */
    @ApiModelProperty(value = "客户id")
    private String customerId;


}
