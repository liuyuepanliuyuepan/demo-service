package cn.klmb.crm.module.member.controller.admin.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

@Data
@ApiModel("放入公海BO")
public class MemberUserPoolBO {

    @ApiModelProperty("客户ID列表")
    private List<String> customerIds;

    @ApiModelProperty("公海ID")
    private String poolId;

    @ApiModelProperty("用户ID")
    private String userId;

    @ApiModelProperty("1分配,2领取")
    private Integer isReceive;
}
