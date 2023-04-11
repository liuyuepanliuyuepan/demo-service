package cn.klmb.crm.module.member.controller.admin.record.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 负责人变更记录 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MemberOwnerRecordRespVO extends MemberOwnerRecordBaseVO {

    @ApiModelProperty(value = "业务id", required = true, example = "02436b85de5f4ba198c724e752686229")
    private String bizId;

    @ApiModelProperty(value = "创建时间", required = true)
    private LocalDateTime createTime;

}
