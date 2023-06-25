package cn.klmb.demo.module.system.service.permission;

import static cn.klmb.demo.framework.common.util.collection.CollectionUtils.convertSet;
import static cn.klmb.demo.framework.common.util.collection.CollectionUtils.singleton;
import static cn.klmb.demo.module.system.constants.SysRedisKeyConstants.SYS_USER_ROLE_USER_ID;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.klmb.demo.framework.common.enums.CommonStatusEnum;
import cn.klmb.demo.framework.common.util.collection.CollectionUtils;
import cn.klmb.demo.module.system.dao.permission.SysRoleMenuMapper;
import cn.klmb.demo.module.system.dao.permission.SysUserRoleMapper;
import cn.klmb.demo.module.system.dto.permission.SysRoleQueryDTO;
import cn.klmb.demo.module.system.entity.permission.SysMenuDO;
import cn.klmb.demo.module.system.entity.permission.SysRoleDO;
import cn.klmb.demo.module.system.entity.permission.SysRoleMenuDO;
import cn.klmb.demo.module.system.entity.permission.SysUserRoleDO;
import com.google.common.collect.Sets;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 权限 Service 实现类
 *
 * @author 快乐萌宝
 */
@Service
@Slf4j
public class SysPermissionServiceImpl implements SysPermissionService {

    private final SysRoleMenuMapper sysRoleMenuMapper;
    private final SysRoleMenuService sysRoleMenuService;
    private final SysUserRoleMapper sysUserRoleMapper;
    private final SysUserRoleService sysUserRoleService;
    private final SysRoleService sysRoleService;
    private final SysMenuService sysMenuService;

    public SysPermissionServiceImpl(SysRoleMenuMapper sysRoleMenuMapper,
            SysRoleMenuService sysRoleMenuService, SysUserRoleMapper sysUserRoleMapper,
            SysUserRoleService sysUserRoleService, SysRoleService sysRoleService,
            @Lazy SysMenuService sysMenuService) {
        this.sysRoleMenuMapper = sysRoleMenuMapper;
        this.sysRoleMenuService = sysRoleMenuService;
        this.sysUserRoleMapper = sysUserRoleMapper;
        this.sysUserRoleService = sysUserRoleService;
        this.sysRoleService = sysRoleService;
        this.sysMenuService = sysMenuService;
    }

    @Override
    public List<SysMenuDO> getRoleMenuList(Collection<String> roleIds,
            Collection<Integer> menuTypes,
            Collection<Integer> menusStatuses) {
        // 任一一个参数为空时，不返回任何菜单
        if (CollectionUtils.isAnyEmpty(roleIds, menuTypes, menusStatuses)) {
            return Collections.emptyList();
        }

        // 判断角色是否包含超级管理员。如果是超级管理员，获取到全部
        List<SysRoleDO> roleList = sysRoleService.getRoles(roleIds);
        if (sysRoleService.hasAnySuperAdmin(roleList)) {
            return sysMenuService.getMenuList(menuTypes, menusStatuses);
        }

        // 获得角色拥有的菜单关联
        List<SysRoleMenuDO> menuList = sysRoleMenuService.getRoleMenuByRoleIds(roleIds);
        Set<String> menuIds = new HashSet<>();
        if (CollUtil.isNotEmpty(menuList)) {
            menuIds = menuList.stream().map(SysRoleMenuDO::getMenuId).collect(Collectors.toSet());
        }
        return sysMenuService.getMenuList(menuIds, menuTypes, menusStatuses);
    }

    @Override
    public List<SysMenuDO> getRoleMenuListByRoleCodes(Collection<String> roleCodes,
            Collection<Integer> menuTypes, Collection<Integer> menusStatuses) {
        // 任一一个参数为空时，不返回任何菜单
        if (CollectionUtils.isAnyEmpty(roleCodes, menuTypes, menusStatuses)) {
            return Collections.emptyList();
        }
        List<SysRoleDO> sysRoles = sysRoleService.list(SysRoleQueryDTO.builder()
                .codes(roleCodes)
                .build());
        if (CollUtil.isEmpty(sysRoles)) {
            return Collections.emptyList();
        }
        List<String> roleIds = sysRoles.stream().map(SysRoleDO::getBizId)
                .collect(Collectors.toList());
        return this.getRoleMenuList(roleIds, menuTypes, menusStatuses);
    }

    @Override
    public Set<String> getUserRoleIds(String userId, Collection<Integer> roleStatuses) {
        List<SysUserRoleDO> userRoleList = sysUserRoleMapper.selectListByUserId(userId);
        // 创建用户的时候没有分配角色，会存在空指针异常
        if (CollUtil.isEmpty(userRoleList)) {
            return Collections.emptySet();
        }
        Set<String> roleIds = userRoleList.stream().map(SysUserRoleDO::getRoleId)
                .collect(Collectors.toSet());
        // 过滤角色状态
        if (CollectionUtil.isNotEmpty(roleStatuses)) {
            roleIds.removeIf(roleId -> {
                SysRoleDO role = sysRoleService.getByBizId(roleId);
                return role == null || !roleStatuses.contains(role.getStatus());
            });
        }
        return roleIds;
    }

    @Override
    public Set<String> getRoleMenuIds(String roleId) {
        // 如果是管理员的情况下，获取全部菜单编号
        if (sysRoleService.hasAnySuperAdmin(Collections.singleton(roleId))) {
            return convertSet(sysMenuService.getMenuList(), SysMenuDO::getBizId);
        }
        // 如果是非管理员的情况下，获得拥有的菜单编号
        return convertSet(sysRoleMenuMapper.selectListByRoleId(roleId), SysRoleMenuDO::getMenuId);
    }

    @Override
    public Set<String> getRoleMenuIds(Collection<String> roleIds) {
        // 如果是管理员的情况下，获取全部菜单编号
        if (sysRoleService.hasAnySuperAdmin(new HashSet<>(roleIds))) {
            return convertSet(sysMenuService.getMenuList(), SysMenuDO::getBizId);
        }
        // 如果是非管理员的情况下，获得拥有的菜单编号
        return convertSet(sysRoleMenuMapper.selectListByRoleIds(roleIds), SysRoleMenuDO::getMenuId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void assignRoleMenu(String roleId, Set<String> menuIds) {
        // 获得角色拥有菜单编号
        Set<String> dbMenuIds = convertSet(sysRoleMenuMapper.selectListByRoleId(roleId),
                SysRoleMenuDO::getMenuId);
        // 计算新增和删除的菜单编号
        Collection<String> createMenuIds = CollUtil.subtract(menuIds, dbMenuIds);
        Collection<String> deleteMenuIds = CollUtil.subtract(dbMenuIds, menuIds);
        // 执行新增和删除。对于已经授权的菜单，不用做任何处理
        if (!CollectionUtil.isEmpty(createMenuIds)) {
            sysRoleMenuService.saveBatch(CollectionUtils.convertList(createMenuIds, menuId -> {
                SysRoleMenuDO entity = new SysRoleMenuDO();
                entity.setRoleId(roleId);
                entity.setMenuId(menuId);
                return entity;
            }));
        }
        if (!CollectionUtil.isEmpty(deleteMenuIds)) {
            sysRoleMenuMapper.deleteListByRoleIdAndMenuIds(roleId, deleteMenuIds);
        }
    }

    @Override
    public Set<String> getUserRoleIdListByUserId(String userId) {
        return convertSet(sysUserRoleMapper.selectListByUserId(userId),
                SysUserRoleDO::getRoleId);
    }

    @Override
    public Set<String> getUserRoleIdListByRoleIds(Collection<String> roleIds) {
        return convertSet(sysUserRoleMapper.selectListByRoleIds(roleIds),
                SysUserRoleDO::getUserId);
    }

    @CacheEvict(cacheNames = SYS_USER_ROLE_USER_ID, key = "#userId")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void assignUserRole(String userId, Set<String> roleIds) {
        // 获得角色拥有角色编号
        Set<String> dbRoleIds = convertSet(sysUserRoleMapper.selectListByUserId(userId),
                SysUserRoleDO::getRoleId);
        // 计算新增和删除的角色编号
        Collection<String> createRoleIds = CollUtil.subtract(roleIds, dbRoleIds);
        Collection<String> deleteMenuIds = CollUtil.subtract(dbRoleIds, roleIds);
        // 执行新增和删除。对于已经授权的角色，不用做任何处理
        if (!CollectionUtil.isEmpty(createRoleIds)) {
            sysUserRoleService.saveBatch(CollectionUtils.convertList(createRoleIds, roleId -> {
                SysUserRoleDO entity = new SysUserRoleDO();
                entity.setUserId(userId);
                entity.setRoleId(roleId);
                return entity;
            }));
        }
        if (!CollectionUtil.isEmpty(deleteMenuIds)) {
            sysUserRoleMapper.deleteListByUserIdAndRoleIdIds(userId, deleteMenuIds);
        }
    }

    @Override
    public void assignRoleDataScope(String roleId, Integer dataScope,
            Set<String> dataScopeDeptIds) {
        sysRoleService.updateRoleDataScope(roleId, dataScope, dataScopeDeptIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void processRoleDeleted(String roleId) {
        // 标记删除 UserRole
        sysUserRoleMapper.deleteListByRoleId(roleId);
        // 标记删除 RoleMenu
        sysRoleMenuMapper.deleteListByRoleId(roleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void processMenuDeleted(String menuId) {
        sysRoleMenuMapper.deleteListByMenuId(menuId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void processUserDeleted(String userId) {
        sysUserRoleMapper.deleteListByUserId(userId);
    }

    @Override
    public boolean hasAnyPermissions(String userId, String... permissions) {
        // 如果为空，说明已经有权限
        if (ArrayUtil.isEmpty(permissions)) {
            return true;
        }

        // 获得当前登录的角色。如果为空，说明没有权限
        Set<String> roleIds = getUserRoleIds(userId,
                singleton(CommonStatusEnum.ENABLE.getStatus()));
        if (CollUtil.isEmpty(roleIds)) {
            return false;
        }
        // 判断是否是超管。如果是，当然符合条件
        if (sysRoleService.hasAnySuperAdmin(roleIds)) {
            return true;
        }

        // 遍历权限，判断是否有一个满足
        return Arrays.stream(permissions).anyMatch(permission -> {
            List<SysMenuDO> menuList = sysMenuService.getMenuListByPermission(permission);
            // 采用严格模式，如果权限找不到对应的 Menu 的话，认为
            if (CollUtil.isEmpty(menuList)) {
                return false;
            }

            // 获得是否拥有该权限，任一一个
            return menuList.stream().anyMatch(menu -> {
                List<SysRoleMenuDO> roleMenuList = sysRoleMenuMapper.selectListByMenuId(menu.getBizId());
                if (CollUtil.isEmpty(roleMenuList)) {
                    return false;
                }
                Set<String> roleSet = roleMenuList.stream().map(SysRoleMenuDO::getRoleId)
                        .collect(Collectors.toSet());
                return CollUtil.containsAny(roleIds, roleSet);
            });
        });
    }

    @Override
    public boolean hasAnyRoles(String userId, String... roles) {
        // 如果为空，说明已经有权限
        if (ArrayUtil.isEmpty(roles)) {
            return true;
        }

        // 获得当前登录的角色。如果为空，说明没有权限
        Set<String> roleIds = getUserRoleIds(userId,
                singleton(CommonStatusEnum.ENABLE.getStatus()));
        if (CollUtil.isEmpty(roleIds)) {
            return false;
        }
        // 判断是否是超管。如果是，当然符合条件
        if (sysRoleService.hasAnySuperAdmin(roleIds)) {
            return true;
        }
        Set<String> userRoles = convertSet(
                sysRoleService.getRoles(roleIds),
                SysRoleDO::getCode);
        return CollUtil.containsAny(userRoles, Sets.newHashSet(roles));
    }

}
