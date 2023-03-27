package cn.klmb.crm.module.system.service.permission;

import cn.klmb.crm.framework.base.core.service.KlmbBaseService;
import cn.klmb.crm.module.system.dto.permission.SysRoleQueryDTO;
import cn.klmb.crm.module.system.entity.permission.SysRoleDO;
import java.util.Collection;
import java.util.List;
import java.util.Set;


/**
 * 系统角色
 *
 * @author shilinchuan
 * @date 2022/11/30
 */
public interface SysRoleService extends KlmbBaseService<SysRoleDO, SysRoleQueryDTO> {

    /**
     * 判断角色数组中，是否有超级管理员
     *
     * @param roleList 角色数组
     * @return 是否有管理员
     */
    boolean hasAnySuperAdmin(Collection<SysRoleDO> roleList);

    /**
     * 判断角色编号数组中，是否有管理员
     *
     * @param ids 角色编号数组
     * @return 是否有管理员
     */
    default boolean hasAnySuperAdmin(Set<String> ids) {
        return hasAnySuperAdmin(this.getRoles(ids));
    }

    /**
     * 设置角色的数据权限
     *
     * @param bizId            角色编号
     * @param dataScope        数据范围
     * @param dataScopeDeptIds 部门编号数组
     */
    void updateRoleDataScope(String bizId, Integer dataScope, Set<String> dataScopeDeptIds);

    /**
     * 根据角色编码查询
     *
     * @param deptId 部门ID
     * @param code   角色编码
     * @return 角色信息
     */
    SysRoleDO getByDeptIdAndCode(String deptId, String code);

    /**
     * 获得所有角色
     *
     * @return 角色列表
     */
    List<SysRoleDO> getAllRoles();

    /**
     * 获得角色数组
     *
     * @param bizIds 角色编号
     * @return 角色列表
     */
    List<SysRoleDO> getRoles(Collection<String> bizIds);

}

