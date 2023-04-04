package cn.klmb.crm.module.member.controller.admin.teamactivity.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - crm团队成员活动创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MemberTeamActivitySaveReqVO extends MemberTeamActivityBaseVO {

}
