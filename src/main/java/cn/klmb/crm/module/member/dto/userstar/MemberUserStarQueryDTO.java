package cn.klmb.crm.module.member.dto.userstar;

import cn.klmb.crm.framework.base.core.annotations.DtoFieldQuery;
import cn.klmb.crm.framework.base.core.pojo.KlmbBaseQueryDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * 用户客户标星关系 DO
 *
 * @author 超级管理员
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class MemberUserStarQueryDTO extends KlmbBaseQueryDTO {

    /**
     * 客户id
     */
    @DtoFieldQuery
    private String customerId;
    /**
     * 用户id
     */
    @DtoFieldQuery
    private String userId;

}
