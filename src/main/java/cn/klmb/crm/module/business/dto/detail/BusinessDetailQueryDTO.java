package cn.klmb.crm.module.business.dto.detail;

import cn.klmb.crm.framework.base.core.annotations.DtoFieldQuery;
import cn.klmb.crm.framework.base.core.annotations.DtoFieldQuery.Operator;
import cn.klmb.crm.framework.base.core.pojo.KlmbBaseQueryDTO;
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
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class BusinessDetailQueryDTO extends KlmbBaseQueryDTO {

    /**
     * 商机状态
     */
    @DtoFieldQuery
    private String businessStatus;
    /**
     * 下次联系时间
     */
    @DtoFieldQuery(queryType = Operator.BETWEEN)
    private LocalDateTime nextTime;
    /**
     * 客户ID
     */
    @DtoFieldQuery
    private String customerId;
    /**
     * 首要联系人ID
     */
    @DtoFieldQuery
    private String contactsId;
    /**
     * 预计成交日期
     */
    @DtoFieldQuery(queryType = Operator.BETWEEN)
    private LocalDateTime dealDate;
    /**
     * 跟进状态 0未跟进1已跟进
     */
    @DtoFieldQuery
    private Integer followup;
    /**
     * 商机名称
     */
    @DtoFieldQuery(queryType = Operator.LIKE)
    private String businessName;
    /**
     * 商机金额
     */
    @DtoFieldQuery
    private BigDecimal money;
    /**
     * 整单折扣
     */
    @DtoFieldQuery
    private BigDecimal discountRate;
    /**
     * 产品总金额
     */
    @DtoFieldQuery
    private BigDecimal totalPrice;
    /**
     * 备注
     */
    @DtoFieldQuery
    private String remark;
    /**
     * 负责人ID
     */
    @DtoFieldQuery
    private String ownerUserId;
    /**
     * 最后跟进时间
     */
    @DtoFieldQuery(queryType = Operator.BETWEEN)
    private LocalDateTime lastTime;

}
