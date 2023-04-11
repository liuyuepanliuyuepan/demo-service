package cn.klmb.crm.module.member.controller.admin.record.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 负责人变更记录创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MemberOwnerRecordSaveReqVO extends MemberOwnerRecordBaseVO {

}
