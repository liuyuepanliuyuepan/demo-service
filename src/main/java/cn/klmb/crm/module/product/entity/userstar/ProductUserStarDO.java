package cn.klmb.crm.module.product.entity.userstar;

import cn.klmb.crm.framework.base.core.entity.KlmbBaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * 用户产品标星关系 DO
 *
 * @author 超级管理员
 */
@TableName(value = "product_user_star", autoResultMap = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class ProductUserStarDO extends KlmbBaseDO {

    /**
     * 产品id
     */
    private String productId;
    /**
     * 用户id
     */
    private String userId;

}
