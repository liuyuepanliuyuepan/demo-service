package cn.klmb.demo.module.system.enums.permission;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 角色所属部门ID 枚举
 */
@Getter
@AllArgsConstructor
public enum SysRoleDeptIdEnum {

    /**
     * 超管虚拟部门ID
     */
    SUPER_DEPT_ID("0");

    private final String id;

}
