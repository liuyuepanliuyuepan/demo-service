package cn.klmb.crm.module.system.dto.permission;

import cn.klmb.crm.framework.base.core.annotations.DtoFieldQuery;
import cn.klmb.crm.framework.base.core.annotations.DtoFieldQuery.Operator;
import cn.klmb.crm.framework.base.core.pojo.KlmbBaseQueryDTO;
import cn.klmb.crm.module.system.enums.permission.DataScopeEnum;
import cn.klmb.crm.module.system.enums.permission.SysRoleTypeEnum;
import java.util.Collection;
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
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class SysRoleQueryDTO extends KlmbBaseQueryDTO {

    /**
     * 角色名称
     */
    @DtoFieldQuery(queryType = Operator.LIKE)
    private String name;

    /**
     * 角色权限字符串
     */
    @DtoFieldQuery(queryType = Operator.LIKE)
    private String code;

    /**
     * 角色权限字符串
     */
    @DtoFieldQuery(queryType = Operator.IN, fieldName = "code")
    private Collection<String> codes;

    /**
     * 显示顺序
     */
    @DtoFieldQuery
    private Integer sort;

    /**
     * 数据范围 枚举 {@link DataScopeEnum}
     */
    @DtoFieldQuery
    private Integer dataScope;

    /**
     * 数据范围(指定部门数组) 适用于 {@link #dataScope} 的值为 {@link DataScopeEnum#DEPT_CUSTOM} 时
     */
    private Set<String> dataScopeDeptIds;

    /**
     * 角色类型 枚举 {@link SysRoleTypeEnum}
     */
    @DtoFieldQuery
    private Integer type;

    /**
     * 备注
     */
    @DtoFieldQuery
    private String remark;

    /**
     * 所属部门ID
     */
    @DtoFieldQuery
    private String deptId;

}
