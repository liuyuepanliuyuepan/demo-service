package cn.klmb.crm.module.system.dto.permission;

import cn.klmb.crm.framework.base.core.annotations.DtoFieldQuery;
import cn.klmb.crm.framework.base.core.pojo.KlmbBaseQueryDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 系统用户-角色
 *
 * @author shilinchuan
 * @date 2022/11/29
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class SysUserRoleQueryDTO extends KlmbBaseQueryDTO {

    /**
     * 用户 ID
     */
    @DtoFieldQuery
    private String userId;

    /**
     * 角色 ID
     */
    @DtoFieldQuery
    private String roleId;

}
