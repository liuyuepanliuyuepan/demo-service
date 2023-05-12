package cn.klmb.crm.module.contract.entity.product;

import cn.klmb.crm.framework.base.core.entity.KlmbBaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName(value = "contract_product", autoResultMap = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class ContractProductDO extends KlmbBaseDO {

    /**
     * 合同ID
     */
    private String contractId;
    /**
     * 产品ID
     */
    private String productId;
    /**
     * 产品单价
     */
    private BigDecimal price;
    /**
     * 销售价格
     */
    private BigDecimal salesPrice;
    /**
     * 数量
     */
    private BigDecimal num;
    /**
     * 折扣
     */
    private BigDecimal discount;
    /**
     * 小计（折扣后价格）
     */
    private BigDecimal subtotal;
    /**
     * 单位
     */
    private String unit;

}
