package cn.klmb.crm.module.member.controller.admin.userpool.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 公海 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MemberUserPoolRespVO extends MemberUserPoolBaseVO {

    @ApiModelProperty(value = "业务id", required = true, example = "71485e9c319e44218573e896015b8136")
    private String bizId;

    @ApiModelProperty(value = "创建时间", required = true)
    private LocalDateTime createTime;

}
