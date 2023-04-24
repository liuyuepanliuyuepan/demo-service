package cn.klmb.crm.module.product.entity.detail;

import cn.klmb.crm.framework.base.core.entity.KlmbBaseDO;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * 产品 DO
 *
 * @author 超级管理员
 */
@TableName(value = "product_detail", autoResultMap = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class ProductDetailDO extends KlmbBaseDO {

    /**
     * 产品名称
     */
    private String name;
    /**
     * 产品编码
     */
    private String num;
    /**
     * 单位
     */
    private String unit;
    /**
     * 价格
     */
    private BigDecimal price;
    /**
     * 产品分类ID
     */
    private String categoryId;
    /**
     * 产品描述
     */
    private String description;
    /**
     * 负责人ID
     */
    private String ownerUserId;

    /**
     * 主图
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> mainFileIds;

    /**
     * 详情图
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> detailFileIds;

    /**
     * 负责人
     */
    @TableField(exist = false)
    private String ownerUserName;


    /**
     * 是否标星
     */
    @TableField(exist = false)
    private Boolean star;


    /**
     * 产品类型
     */
    @TableField(exist = false)
    private String categoryName;

}
