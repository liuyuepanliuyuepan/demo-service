package cn.klmb.crm.module.member.controller.admin.record.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 负责人变更记录更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MemberOwnerRecordUpdateReqVO extends MemberOwnerRecordBaseVO {

    @ApiModelProperty(value = "业务id", required = true, example = "02436b85de5f4ba198c724e752686229")
    @NotNull(message = "业务id不能为空")
    private String bizId;

}
