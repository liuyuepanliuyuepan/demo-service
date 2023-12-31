package cn.klmb.demo.module.system.controller.admin.permission.vo.permission;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Collections;
import java.util.Set;
import javax.validation.constraints.NotNull;
import lombok.Data;

@ApiModel("管理后台 - 赋予角色数据权限 Request VO")
@Data
public class SysPermissionAssignRoleDataScopeReqVO {

    @ApiModelProperty(value = "角色编号", required = true, example = "1")
    @NotNull(message = "角色编号不能为空")
    private String roleId;

    @ApiModelProperty(value = "数据范围", required = true, example = "1", notes = "参见 DataScopeEnum 枚举类", hidden = true)
    @NotNull(message = "数据范围不能为空")
    private Integer dataScope;

    @ApiModelProperty(value = "部门编号列表", example = "1,3,5", notes = "只有范围类型为 DEPT_CUSTOM 时，该字段才需要", hidden = true)
    private Set<String> dataScopeDeptIds = Collections.emptySet(); // 兜底

}
