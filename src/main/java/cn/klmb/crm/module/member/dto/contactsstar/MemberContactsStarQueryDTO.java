package cn.klmb.crm.module.member.dto.contactsstar;

import cn.klmb.crm.framework.base.core.annotations.DtoFieldQuery;
import cn.klmb.crm.framework.base.core.pojo.KlmbBaseQueryDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * 用户联系人标星关系 DO
 *
 * @author 超级管理员
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class MemberContactsStarQueryDTO extends KlmbBaseQueryDTO {

    /**
     * 联系人id
     */
    @DtoFieldQuery
    private String contactsId;
    /**
     * 用户id
     */
    @DtoFieldQuery
    private String userId;

}
