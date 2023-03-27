package cn.klmb.crm.module.system.service.permission;

import cn.klmb.crm.framework.base.core.service.KlmbBaseService;
import cn.klmb.crm.module.system.dto.permission.SysRoleMenuQueryDTO;
import cn.klmb.crm.module.system.entity.permission.SysRoleMenuDO;
import java.util.Collection;
import java.util.List;

/**
 * 系統角色菜单
 *
 * @author shilinchuan
 * @date 2022/11/29
 */
public interface SysRoleMenuService extends KlmbBaseService<SysRoleMenuDO, SysRoleMenuQueryDTO> {

    /**
     * 获取所有角色菜单
     *
     * @return 角色菜单列表
     */
    List<SysRoleMenuDO> getAllRoleMenu();

    /**
     * 根据角色ID列表获取所有角色菜单
     *
     * @param roleIds 角色ID列表
     * @return 角色菜单列表
     */
    List<SysRoleMenuDO> getRoleMenuByRoleIds(Collection<String> roleIds);

}

