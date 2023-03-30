package cn.klmb.crm.module.member.controller.admin.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

@ApiModel("管理后台 - 客户-用户批量修改状态 Request VO")
@Data
@ToString(callSuper = true)
public class MemberUserBatchUpdateReqVO {

    @ApiModelProperty(value = "业务ids", required = true)
    @NotEmpty(message = "业务ids不能为空")
    private List<String> bizIds;

    @ApiModelProperty(value = "成交状态 0 未成交 1 已成交")
    private Integer dealStatus;

}
