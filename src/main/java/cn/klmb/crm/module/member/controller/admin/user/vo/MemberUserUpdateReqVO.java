package cn.klmb.crm.module.member.controller.admin.user.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;
import javax.validation.constraints.*;

@ApiModel("管理后台 - 客户-用户更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MemberUserUpdateReqVO extends MemberUserBaseVO {

    @ApiModelProperty(value = "业务id", required = true, example = "53af90a6a42e441185af511c229cd5d0")
    @NotNull(message = "业务id不能为空")
    private String bizId;

}
