package cn.klmb.crm.module.system.enums.permission;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 角色类型
 *
 * @author shilinchuan
 * @date 2022/11/30
 */
@Getter
@AllArgsConstructor
public enum SysRoleTypeEnum {

    /**
     * 内置角色
     */
    SYSTEM(1),
    /**
     * 自定义角色
     */
    CUSTOM(2);

    private final Integer type;

}
