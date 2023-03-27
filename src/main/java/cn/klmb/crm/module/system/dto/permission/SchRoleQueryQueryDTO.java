package cn.klmb.crm.module.system.dto.permission;

import cn.klmb.crm.framework.base.core.annotations.DtoFieldQuery;
import cn.klmb.crm.framework.base.core.annotations.DtoFieldQuery.Operator;
import cn.klmb.crm.framework.base.core.pojo.KlmbBaseQueryDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 学校角色查询DTO
 *
 * @author shilinchuan
 * @date 2023/1/3
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class SchRoleQueryQueryDTO extends KlmbBaseQueryDTO {

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
     * 部门ID
     */
    private String deptId;

}
