package cn.klmb.crm.module.member.controller.admin.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.ToString;

@ApiModel("管理后台 - 客户-用户批量删除 Request VO")
@Data
@ToString(callSuper = true)
public class MemberUserDeleteReqVO {

    @ApiModelProperty(value = "业务ids", required = true)
    @NotEmpty(message = "业务ids不能为空")
    private List<String> bizIds;


}
