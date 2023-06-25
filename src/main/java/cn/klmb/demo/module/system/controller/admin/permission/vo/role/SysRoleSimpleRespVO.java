package cn.klmb.demo.module.system.controller.admin.permission.vo.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统角色 - 角色信息精简
 *
 * @author liuyuepan
 * @date 2022/11/30
 */
@ApiModel(description = "系统角色 - 角色信息精简")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysRoleSimpleRespVO {

    @ApiModelProperty(value = "角色编号", required = true, example = "1")
    private String bizId;

    @ApiModelProperty(value = "角色名称", required = true, example = "管理员")
    private String name;

    @ApiModelProperty(value = "角色编码", required = true, example = "ADMIN")
    private String code;

}
