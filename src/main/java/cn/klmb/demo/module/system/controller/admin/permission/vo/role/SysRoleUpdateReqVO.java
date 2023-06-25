package cn.klmb.demo.module.system.controller.admin.permission.vo.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统角色 - 更新
 *
 * @author liuyuepan
 * @date 2022/11/30
 */
@ApiModel(description = "系统角色 - 更新")
@EqualsAndHashCode(callSuper = true)
@Data
public class SysRoleUpdateReqVO extends SysRoleBaseVO {

    @ApiModelProperty(value = "角色编码", required = true, example = "uuid")
    @NotNull(message = "角色编码不能为空")
    private String bizId;

}
