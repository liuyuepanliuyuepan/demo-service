package cn.klmb.crm.module.member.dto.team;

import cn.klmb.crm.framework.base.core.annotations.DtoFieldQuery;
import cn.klmb.crm.framework.base.core.pojo.KlmbBaseQueryDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * crm团队成员 DO
 *
 * @author 超级管理员
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class MemberTeamQueryDTO extends KlmbBaseQueryDTO {

    /**
     * 用户ID
     */
    @DtoFieldQuery
    private String userId;
    /**
     * 权限(1 只读 2 读写 3 负责人)
     */
    @DtoFieldQuery
    private Integer power;

    /**
     * 类型，同crm类型
     */
    @DtoFieldQuery
    private Integer type;
    /**
     * 对应类型主键ID
     */
    @DtoFieldQuery
    private String typeId;

}
