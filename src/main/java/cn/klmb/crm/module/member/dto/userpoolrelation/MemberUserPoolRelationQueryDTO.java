package cn.klmb.crm.module.member.dto.userpoolrelation;


import cn.klmb.crm.framework.base.core.annotations.DtoFieldQuery;
import cn.klmb.crm.framework.base.core.pojo.KlmbBaseQueryDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * 客户公海关联 DO
 *
 * @author 超级管理员
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class MemberUserPoolRelationQueryDTO extends KlmbBaseQueryDTO {

    /**
     * 客户id
     */
    @DtoFieldQuery
    private String customerId;
    /**
     * 公海id
     */
    @DtoFieldQuery
    private String poolId;

}
