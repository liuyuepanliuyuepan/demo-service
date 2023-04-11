package cn.klmb.crm.module.member.controller.admin.userpoolrelation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 客户公海关联 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MemberUserPoolRelationRespVO extends MemberUserPoolRelationBaseVO {

    @ApiModelProperty(value = "业务id", required = true, example = "2501cb266cb44e679d7768c01854a92c")
    private String bizId;

    @ApiModelProperty(value = "创建时间", required = true)
    private LocalDateTime createTime;

}
