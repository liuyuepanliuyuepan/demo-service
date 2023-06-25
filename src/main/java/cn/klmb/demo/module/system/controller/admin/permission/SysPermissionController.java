package cn.klmb.demo.module.system.controller.admin.permission;

import static cn.klmb.demo.framework.common.pojo.CommonResult.success;

import cn.klmb.demo.framework.common.pojo.CommonResult;
import cn.klmb.demo.module.system.controller.admin.permission.vo.permission.SysPermissionAssignRoleDataScopeReqVO;
import cn.klmb.demo.module.system.controller.admin.permission.vo.permission.SysPermissionAssignRoleMenuReqVO;
import cn.klmb.demo.module.system.controller.admin.permission.vo.permission.SysPermissionAssignUserRoleReqVO;
import cn.klmb.demo.module.system.service.permission.SysPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.Set;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统管理 - 权限管理
 *
 * @author liuyuepan
 * @date 2022/12/3
 */
@Api(tags = "0104.系统管理-权限管理")
@RestController
@RequestMapping("/system/permission")
public class SysPermissionController {

    @Resource
    private SysPermissionService sysPermissionService;

    @ApiOperation("获得角色拥有的菜单编号")
    @ApiImplicitParam(name = "roleId", value = "角色编号", required = true, dataTypeClass = String.class)
    @GetMapping("/list-role-resources")
    @PreAuthorize("@ss.hasPermission('system:permission:assign-role-menu')")
    public CommonResult<Set<String>> listRoleMenus(String roleId) {
        return success(sysPermissionService.getRoleMenuIds(roleId));
    }

    @PostMapping("/assign-role-menu")
    @ApiOperation("赋予角色菜单")
    @PreAuthorize("@ss.hasPermission('system:permission:assign-role-menu')")
    public CommonResult<Boolean> assignRoleMenu(
            @Validated @RequestBody SysPermissionAssignRoleMenuReqVO reqVO) {
        // 执行菜单的分配
        sysPermissionService.assignRoleMenu(reqVO.getRoleId(), reqVO.getMenuIds());
        return success(true);
    }

    // todo 暂未实现
    @PostMapping("/assign-role-data-scope")
    @ApiOperation(value = "赋予角色数据权限", hidden = true)
    @PreAuthorize("@ss.hasPermission('system:permission:assign-role-data-scope')")
    public CommonResult<Boolean> assignRoleDataScope(
            @Valid @RequestBody SysPermissionAssignRoleDataScopeReqVO reqVO) {
        sysPermissionService.assignRoleDataScope(reqVO.getRoleId(), reqVO.getDataScope(),
                reqVO.getDataScopeDeptIds());
        return success(true);
    }

    @ApiOperation("获得用户拥有的角色编号列表")
    @ApiImplicitParam(name = "userId", value = "用户编号", required = true, dataTypeClass = String.class)
    @GetMapping("/list-user-roles")
    @PreAuthorize("@ss.hasPermission('system:permission:assign-user-role')")
    public CommonResult<Set<String>> listAdminRoles(@RequestParam("userId") String userId) {
        return success(sysPermissionService.getUserRoleIdListByUserId(userId));
    }

    @ApiOperation("赋予用户角色")
    @PostMapping("/assign-user-role")
    @PreAuthorize("@ss.hasPermission('system:permission:assign-user-role')")
    public CommonResult<Boolean> assignUserRole(
            @Validated @RequestBody SysPermissionAssignUserRoleReqVO reqVO) {
        sysPermissionService.assignUserRole(reqVO.getUserId(), reqVO.getRoleIds());
        return success(true);
    }

}
