package cn.klmb.crm.module.business.entity.detail;

import cn.klmb.crm.framework.base.core.entity.KlmbBaseDO;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * 商机 DO
 *
 * @author 超级管理员
 */
@TableName(value = "business_detail", autoResultMap = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class BusinessDetailDO extends KlmbBaseDO {

    /**
     * 商机状态
     */
    private String businessStatus;
    /**
     * 下次联系时间
     */
    private LocalDateTime nextTime;
    /**
     * 客户ID
     */
    private String customerId;
    /**
     * 首要联系人ID
     */
    private String contactsId;
    /**
     * 预计成交日期
     */
    private LocalDateTime dealDate;
    /**
     * 跟进状态 0未跟进1已跟进
     */
    private Integer followup;
    /**
     * 商机名称
     */
    private String businessName;
    /**
     * 商机金额
     */
    private BigDecimal money;
    /**
     * 整单折扣
     */
    private BigDecimal discountRate;
    /**
     * 产品总金额
     */
    private BigDecimal totalPrice;
    /**
     * 备注
     */
    private String remark;
    /**
     * 负责人ID
     */
    private String ownerUserId;
    /**
     * 最后跟进时间
     */
    private LocalDateTime lastTime;


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
     * 客户名称
     */
    @TableField(exist = false)
    private String customerName;

}
