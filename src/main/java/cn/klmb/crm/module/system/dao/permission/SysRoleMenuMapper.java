package cn.klmb.crm.module.system.dao.permission;


import static cn.klmb.crm.module.system.constants.SysRedisKeyConstants.SYS_ROLE_MENU;
import static cn.klmb.crm.module.system.constants.SysRedisKeyConstants.SYS_ROLE_MENU_MENU_ID;

import cn.klmb.crm.framework.base.core.dao.KlmbBaseMapper;
import cn.klmb.crm.module.system.dto.permission.SysRoleMenuQueryDTO;
import cn.klmb.crm.module.system.entity.permission.SysRoleMenuDO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import java.util.Collection;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;


/**
 * 角色菜单
 *
 * @author shilinchuan
 * @date 2022/11/30
 */
@Mapper
public interface SysRoleMenuMapper extends KlmbBaseMapper<SysRoleMenuDO, SysRoleMenuQueryDTO> {

    default List<SysRoleMenuDO> selectListByRoleId(String roleId) {
        return selectList(new QueryWrapper<SysRoleMenuDO>().eq("role_id", roleId));
    }

    default List<SysRoleMenuDO> selectListByRoleIds(Collection<String> roleIds) {
        return selectList(new QueryWrapper<SysRoleMenuDO>().in("role_id", roleIds));
    }

    @Cacheable(cacheNames = SYS_ROLE_MENU_MENU_ID, key = "#menuId", unless = "#result == null")
    default List<SysRoleMenuDO> selectListByMenuId(String menuId) {
        return selectList(new QueryWrapper<SysRoleMenuDO>().in("menu_id", menuId));
    }

    @CacheEvict(cacheNames = {SYS_ROLE_MENU, SYS_ROLE_MENU_MENU_ID}, allEntries = true)
    default void deleteListByRoleIdAndMenuIds(String roleId, Collection<String> menuIds) {
        delete(new QueryWrapper<SysRoleMenuDO>().eq("role_id", roleId)
                .in("menu_id", menuIds));
    }

    @CacheEvict(cacheNames = {SYS_ROLE_MENU, SYS_ROLE_MENU_MENU_ID}, allEntries = true)
    default void deleteListByMenuId(String menuId) {
        delete(new QueryWrapper<SysRoleMenuDO>().eq("menu_id", menuId));
    }

    @CacheEvict(cacheNames = {SYS_ROLE_MENU, SYS_ROLE_MENU_MENU_ID}, allEntries = true)
    default void deleteListByRoleId(String roleId) {
        delete(new QueryWrapper<SysRoleMenuDO>().eq("role_id", roleId));
    }

}
