package cn.klmb.crm.module.contract.dto.product;

import cn.klmb.crm.framework.base.core.annotations.DtoFieldQuery;
import cn.klmb.crm.framework.base.core.annotations.DtoFieldQuery.Operator;
import cn.klmb.crm.framework.base.core.pojo.KlmbBaseQueryDTO;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * 合同产品关系 DO
 *
 * @author 超级管理员
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class ContractProductQueryDTO extends KlmbBaseQueryDTO {

    /**
     * 合同ID
     */
    @DtoFieldQuery
    private String contractId;
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
     * 折扣
     */
    @DtoFieldQuery
    private BigDecimal discount;
    /**
     * 小计（折扣后价格）
     */
    @DtoFieldQuery
    private BigDecimal subtotal;
    /**
     * 单位
     */
    @DtoFieldQuery
    private String unit;

}
