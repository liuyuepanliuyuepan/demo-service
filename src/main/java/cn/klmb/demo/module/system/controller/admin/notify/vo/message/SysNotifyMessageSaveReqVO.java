package cn.klmb.demo.module.system.controller.admin.notify.vo.message;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 站内信创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SysNotifyMessageSaveReqVO extends SysNotifyMessageBaseVO {

}
