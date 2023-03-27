package cn.klmb.crm.module.system.entity.permission;

import cn.klmb.crm.framework.base.core.entity.KlmbBaseDO;
import cn.klmb.crm.framework.mybatis.core.type.JsonLongSetTypeHandler;
import cn.klmb.crm.module.system.enums.permission.DataScopeEnum;
import cn.klmb.crm.module.system.enums.permission.SysRoleTypeEnum;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 系统角色
 *
 * @author shilinchuan
 * @date 2022/11/30
 */
@TableName("sys_role")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class SysRoleDO extends KlmbBaseDO {

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色权限字符串
     */
    private String code;

    /**
     * 显示顺序
     */
    private Integer sort;

    /**
     * 数据范围 枚举 {@link DataScopeEnum}
     */
    private Integer dataScope;

    /**
     * 数据范围(指定部门数组) 适用于 {@link #dataScope} 的值为 {@link DataScopeEnum#DEPT_CUSTOM} 时
     */
    @TableField(typeHandler = JsonLongSetTypeHandler.class)
    private Set<String> dataScopeDeptIds;

    /**
     * 角色类型 枚举 {@link SysRoleTypeEnum}
     */
    private Integer type;

    /**
     * 备注
     */
    private String remark;

    /**
     * 所属部门ID
     */
    private String deptId;

}
