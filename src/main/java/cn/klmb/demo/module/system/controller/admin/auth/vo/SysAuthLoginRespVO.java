package cn.klmb.demo.module.system.controller.admin.auth.vo;

import cn.klmb.demo.module.system.controller.admin.user.vo.SysUserRespVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("管理后台 - 登录 Response VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SysAuthLoginRespVO {

    @ApiModelProperty(value = "用户编号", required = true, example = "1")
    private String userId;

    @ApiModelProperty(value = "访问令牌", required = true, example = "happy")
    private String accessToken;

    @ApiModelProperty(value = "刷新令牌", required = true, example = "nice")
    private String refreshToken;

    @ApiModelProperty(value = "过期时间", required = true)
    private LocalDateTime expiresTime;

    @ApiModelProperty(value = "用户信息", required = true)
    private SysUserRespVO userInfo;

}
