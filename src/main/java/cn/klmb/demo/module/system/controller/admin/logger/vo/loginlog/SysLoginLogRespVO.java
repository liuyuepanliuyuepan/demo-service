package cn.klmb.demo.module.system.controller.admin.logger.vo.loginlog;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统管理 - 登录日志
 *
 * @author liuyuepan
 * @date 2022/12/12
 */
@ApiModel(description = "系统管理 - 登录日志")
@EqualsAndHashCode(callSuper = true)
@Data
public class SysLoginLogRespVO extends SysLoginLogBaseVO {

    @ApiModelProperty(value = "日志编号", required = true, example = "uuid")
    private String bizId;

    @ApiModelProperty(value = "用户编号", example = "uuid")
    private String userId;

    @ApiModelProperty(value = "用户类型", required = true, example = "2", notes = "参见 UserTypeEnum 枚举")
    @NotNull(message = "用户类型不能为空")
    private Integer userType;

    @ApiModelProperty(value = "登录时间", required = true)
    private LocalDateTime createTime;

}
