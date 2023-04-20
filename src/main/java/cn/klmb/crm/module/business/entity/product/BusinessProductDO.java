package cn.klmb.crm.module.business.entity.product;

import cn.klmb.crm.framework.base.core.entity.KlmbBaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName(value = "business_product", autoResultMap = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class BusinessProductDO extends KlmbBaseDO {

    /**
     * 商机ID
     */
    private String businessId;
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
     * 小计（折扣后价格）
     */
    private BigDecimal subtotal;
    /**
     * 折扣
     */
    private Integer discount;
    /**
     * 单位
     */
    private String unit;

}
