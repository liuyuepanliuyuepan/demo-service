package cn.klmb.crm.module.business.dto.contacts;

import cn.klmb.crm.framework.base.core.annotations.DtoFieldQuery;
import cn.klmb.crm.framework.base.core.pojo.KlmbBaseQueryDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * 商机联系人关联 DO
 *
 * @author 超级管理员
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class BusinessContactsQueryDTO extends KlmbBaseQueryDTO {

    /**
     * 商机
     */
    @DtoFieldQuery
    private String businessId;
    /**
     * 联系人
     */
    @DtoFieldQuery
    private String contactsId;

}
