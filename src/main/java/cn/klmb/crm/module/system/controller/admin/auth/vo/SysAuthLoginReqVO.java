package cn.klmb.crm.module.system.controller.admin.auth.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@ApiModel(value = "管理后台 - 账号密码登录 Request VO", description = "如果登录并绑定社交用户，需要传递 social 开头的参数")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SysAuthLoginReqVO {

    @ApiModelProperty(value = "账号", required = true, example = "super_admin")
    @NotEmpty(message = "登录账号不能为空")
    @Length(min = 4, max = 30, message = "账号长度为 4-30 位")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "用户账号由 数字、字母、_ 组成")
    private String username;

    @ApiModelProperty(value = "密码", required = true, example = "123456")
    @NotEmpty(message = "密码不能为空")
    @Length(min = 4, max = 16, message = "密码长度为 4-16 位")
    private String password;


    /**
     * 飞书小程序登录时获取的 code
     */
    @ApiModelProperty(value = "飞书小程序登录时获取的 code")
    private String code;

}
