package cn.klmb.crm.module.system.service.permission;

import cn.klmb.crm.framework.base.core.service.KlmbBaseService;
import cn.klmb.crm.module.system.dto.permission.SysMenuQueryDTO;
import cn.klmb.crm.module.system.entity.permission.SysMenuDO;
import java.util.Collection;
import java.util.List;


/**
 * 系统菜单
 *
 * @author shilinchuan
 * @date 2022/11/30
 */
public interface SysMenuService extends KlmbBaseService<SysMenuDO, SysMenuQueryDTO> {

    /**
     * 获取所有生效的菜单
     *
     * @return 获取所有生效的菜单
     */
    List<SysMenuDO> getMenuList();

    /**
     * 获得所有菜单
     * <p>
     * 任一参数为空时，则返回为空
     *
     * @param menuTypes     菜单类型数组
     * @param menusStatuses 菜单状态数组
     * @return 菜单列表
     */
    List<SysMenuDO> getMenuList(Collection<Integer> menuTypes,
            Collection<Integer> menusStatuses);

    /**
     * 获得指定编号的菜单数组
     * <p>
     * 任一参数为空时，则返回为空
     *
     * @param menuIds       菜单编号数组
     * @param menuTypes     菜单类型数组
     * @param menusStatuses 菜单状态数组
     * @return 菜单数组
     */
    List<SysMenuDO> getMenuList(Collection<String> menuIds, Collection<Integer> menuTypes,
            Collection<Integer> menusStatuses);

    /**
     * 获得权限对应的菜单数组
     *
     * @param permission 权限标识
     * @return 数组
     */
    List<SysMenuDO> getMenuListByPermission(String permission);

}

