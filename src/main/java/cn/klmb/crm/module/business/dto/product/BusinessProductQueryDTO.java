package cn.klmb.crm.module.business.dto.product;

import cn.klmb.crm.framework.base.core.annotations.DtoFieldQuery;
import cn.klmb.crm.framework.base.core.annotations.DtoFieldQuery.Operator;
import cn.klmb.crm.framework.base.core.pojo.KlmbBaseQueryDTO;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * 商机产品关系 DO
 *
 * @author 超级管理员
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class BusinessProductQueryDTO extends KlmbBaseQueryDTO {

    /**
     * 商机ID
     */
    @DtoFieldQuery(queryType = Operator.IN)
    private String businessId;
    /**
     * 产品ID
     */
    @DtoFieldQuery
    private String productId;
    /**
     * 产品单价
     */
    @DtoFieldQuery
    private BigDecimal price;
    /**
     * 销售价格
     */
    @DtoFieldQuery
    private BigDecimal salesPrice;
    /**
     * 数量
     */
    @DtoFieldQuery
    private BigDecimal num;
    /**
     * 小计（折扣后价格）
     */
    @DtoFieldQuery
    private BigDecimal subtotal;
    /**
     * 折扣
     */
    @DtoFieldQuery
    private Integer discount;
    /**
     * 单位
     */
    @DtoFieldQuery
    private String unit;

}
