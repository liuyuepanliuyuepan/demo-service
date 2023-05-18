package cn.klmb.crm.module.contract.dto.detail;

import cn.klmb.crm.framework.base.core.annotations.DtoFieldQuery;
import cn.klmb.crm.framework.base.core.annotations.DtoFieldQuery.Operator;
import cn.klmb.crm.framework.base.core.annotations.DtoKeywordQuery;
import cn.klmb.crm.framework.base.core.pojo.KlmbBaseQueryDTO;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * 合同详情 DO
 *
 * @author 超级管理员
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class ContractDetailQueryDTO extends KlmbBaseQueryDTO {

    /**
     * 合同名称
     */
    @DtoKeywordQuery
    private String name;
    /**
     * 客户id
     */
    @DtoFieldQuery
    private String memberUserId;
    /**
     * 商机id
     */
    @DtoFieldQuery
    private String businessId;
    /**
     * 下单日期
     */
    @DtoFieldQuery(queryType = Operator.BETWEEN)
    private LocalDateTime orderDate;
    /**
     * 负责人ID
     */
    @DtoFieldQuery
    private String ownerUserId;
    /**
     * 合同编号
     */
    @DtoKeywordQuery
    private String num;
    /**
     * 开始时间
     */
    @DtoFieldQuery(queryType = Operator.BETWEEN)
    private LocalDateTime startTime;
    /**
     * 结束时间
     */
    @DtoFieldQuery(queryType = Operator.BETWEEN)
    private LocalDateTime endTime;
    /**
     * 合同金额
     */
    @DtoFieldQuery
    private BigDecimal money;
    /**
     * 客户签约人（联系人id）
     */
    @DtoFieldQuery
    private String contactsId;
    /**
     * 公司签约人
     */
    @DtoFieldQuery
    private String companyUserId;
    /**
     * 备注
     */
    @DtoFieldQuery
    private String remark;
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
     * 合同类型
     */
    @DtoFieldQuery
    private String types;
    /**
     * 付款方式
     */
    @DtoFieldQuery
    private String paymentType;

    /**
     * 最后跟进时间
     */
    @DtoFieldQuery(queryType = Operator.BETWEEN)
    private LocalDateTime lastTime;
    /**
     * 回款总金额
     */
    @DtoFieldQuery
    private BigDecimal receivedMoney;
    /**
     * 未回款总金额
     */
    @DtoFieldQuery
    private BigDecimal unreceivedMoney;
    /**
     * 未回款总金额-复制使用
     */
    @DtoFieldQuery
    private String oldBizId;
    /**
     * 负责人id列表
     */
    @DtoFieldQuery(queryType = Operator.IN, fieldName = "ownerUserId")
    private List<String> ownerUserIds;

    /**
     * 客户id列表
     */
    @DtoFieldQuery(queryType = Operator.IN, fieldName = "memberUserId")
    private List<String> memberUserIds;


}
