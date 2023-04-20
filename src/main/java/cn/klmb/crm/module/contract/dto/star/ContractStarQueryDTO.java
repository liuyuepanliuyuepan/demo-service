package cn.klmb.crm.module.contract.dto.star;

import cn.klmb.crm.framework.base.core.annotations.DtoFieldQuery;
import cn.klmb.crm.framework.base.core.annotations.DtoFieldQuery.Operator;
import cn.klmb.crm.framework.base.core.pojo.KlmbBaseQueryDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * 合同标星关系 DO
 *
 * @author 超级管理员
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class ContractStarQueryDTO extends KlmbBaseQueryDTO {

    /**
     * 合同ID
     */
    @DtoFieldQuery
    private String contractId;
    /**
     * 用户id
     */
    @DtoFieldQuery
    private String userId;

}
