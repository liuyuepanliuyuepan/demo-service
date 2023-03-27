package cn.klmb.crm.framework.security.core.service;


import static cn.klmb.crm.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

import cn.hutool.core.collection.CollUtil;
import cn.klmb.crm.framework.security.core.LoginUser;
import cn.klmb.crm.framework.security.core.util.SecurityFrameworkUtils;
import cn.klmb.crm.module.system.service.permission.SysPermissionService;
import java.util.Arrays;
import lombok.AllArgsConstructor;

/**
 * 默认的 {@link SecurityFrameworkService} 实现类
 *
 * @author 快乐萌宝
 */
@AllArgsConstructor
public class SecurityFrameworkServiceImpl implements SecurityFrameworkService {

    private SysPermissionService sysPermissionService;

    @Override
    public boolean hasPermission(String permission) {
        return hasAnyPermissions(permission);
    }

    @Override
    public boolean hasAnyPermissions(String... permissions) {
        return sysPermissionService.hasAnyPermissions(getLoginUserId(), permissions);
    }

    @Override
    public boolean hasRole(String role) {
        return hasAnyRoles(role);
    }

    @Override
    public boolean hasAnyRoles(String... roles) {
        return sysPermissionService.hasAnyRoles(getLoginUserId(), roles);
    }

    @Override
    public boolean hasScope(String scope) {
        return hasAnyScopes(scope);
    }

    @Override
    public boolean hasAnyScopes(String... scope) {
        LoginUser user = SecurityFrameworkUtils.getLoginUser();
        if (user == null) {
            return false;
        }
        return CollUtil.containsAny(user.getScopes(), Arrays.asList(scope));
    }

}
