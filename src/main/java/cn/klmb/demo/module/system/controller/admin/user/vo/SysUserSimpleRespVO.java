package cn.klmb.demo.module.system.controller.admin.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统用户 - 用户信息精简
 *
 * @author liuyuepan
 * @date 2022/11/29
 */
@ApiModel(description = "系统用户 - 用户信息精简")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysUserSimpleRespVO {

    @ApiModelProperty(value = "用户业务id", required = true)
    private String bizId;

    @ApiModelProperty(value = "用户昵称", required = true)
    private String nickname;

    @ApiModelProperty(value = "真实姓名", required = true)
    private String realname;


}
