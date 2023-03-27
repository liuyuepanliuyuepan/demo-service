package cn.klmb.crm.module.system.dto.permission;

import cn.klmb.crm.framework.base.core.annotations.DtoFieldQuery;
import cn.klmb.crm.framework.base.core.annotations.DtoFieldQuery.Operator;
import cn.klmb.crm.framework.base.core.pojo.KlmbBaseQueryDTO;
import java.util.Collection;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 系统角色-菜单
 *
 * @author shilinchuan
 * @date 2022/11/29
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class SysRoleMenuQueryDTO extends KlmbBaseQueryDTO {

    /**
     * 角色 ID
     */
    @DtoFieldQuery
    private String roleId;

    /**
     * 角色 ID 列表
     */
    @DtoFieldQuery(queryType = Operator.IN, fieldName = "roleId")
    private Collection<String> roleIds;

    /**
     * 菜单 ID
     */
    @DtoFieldQuery
    private String menuId;

    /**
     * 菜单 ID 列表
     */
    @DtoFieldQuery(queryType = Operator.IN, fieldName = "menuId")
    private Collection<String> menuIds;

}
