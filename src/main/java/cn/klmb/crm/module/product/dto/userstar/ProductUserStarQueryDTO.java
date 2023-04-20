package cn.klmb.crm.module.product.dto.userstar;

import cn.klmb.crm.framework.base.core.annotations.DtoFieldQuery;
import cn.klmb.crm.framework.base.core.pojo.KlmbBaseQueryDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * 用户产品标星关系 DO
 *
 * @author 超级管理员
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class ProductUserStarQueryDTO extends KlmbBaseQueryDTO {

    /**
     * 产品id
     */
    @DtoFieldQuery
    private String productId;
    /**
     * 用户id
     */
    @DtoFieldQuery
    private String userId;

}
