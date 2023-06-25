package cn.klmb.demo.module.system.enums.permission;

import cn.klmb.demo.framework.common.util.object.ObjectUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 角色标识枚举
 *
 * @author liuyuepan
 * @date 2022/12/29
 */
@Getter
@AllArgsConstructor
public enum SysRoleCodeEnum {

    SUPER_ADMIN("super_admin", "超级管理员"),
    SYS_ADMIN("sys_admin", "超级管理员(系统)"),
    SCH_ADMIN("sch_admin", "超级管理员(学校)"),
    EDU_ADMIN("edu_admin", "超级管理员(教育局)"),
    TEACHER("teacher", "教师"),
    ;

    /**
     * 角色编码
     */
    private final String code;
    /**
     * 名字
     */
    private final String name;

    public static boolean isSuperAdmin(String code) {
        return ObjectUtils.equalsAny(code, SUPER_ADMIN.getCode());
    }

}
