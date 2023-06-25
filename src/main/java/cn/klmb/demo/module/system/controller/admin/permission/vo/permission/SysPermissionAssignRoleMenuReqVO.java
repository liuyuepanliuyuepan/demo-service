package cn.klmb.demo.module.system.controller.admin.permission.vo.permission;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Collections;
import java.util.Set;
import javax.validation.constraints.NotNull;
import lombok.Data;

@ApiModel("管理后台 - 赋予角色菜单 Request VO")
@Data
public class SysPermissionAssignRoleMenuReqVO {

    @ApiModelProperty(value = "角色编号", required = true, example = "uuid")
    @NotNull(message = "角色编号不能为空")
    private String roleId;

    @ApiModelProperty(value = "菜单编号列表", example = "['uuid1','uuid2']")
    private Set<String> menuIds = Collections.emptySet(); // 兜底

}
