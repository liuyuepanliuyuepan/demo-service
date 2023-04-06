package cn.klmb.crm.module.member.controller.admin.bis.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 客户工商信息 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MemberBisRespVO extends MemberBisBaseVO {

    @ApiModelProperty(value = "业务id", required = true, example = "7eff7296786d4152bd4b164ae0766f9e")
    private String bizId;

    @ApiModelProperty(value = "创建时间", required = true)
    private LocalDateTime createTime;

}
