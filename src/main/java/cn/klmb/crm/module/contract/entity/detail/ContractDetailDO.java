package cn.klmb.crm.module.contract.entity.detail;

import cn.klmb.crm.framework.base.core.annotations.DtoFieldQuery;
import cn.klmb.crm.framework.base.core.entity.KlmbBaseDO;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * 合同详情 DO
 *
 * @author 超级管理员
 */
@TableName(value = "contract_detail", autoResultMap = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class ContractDetailDO extends KlmbBaseDO {

    /**
     * 合同名称
     */
    private String name;
    /**
     * 客户id
     */
    private String memberUserId;
    /**
     * 商机id
     */
    private String businessId;
    /**
     * 下单日期
     */
    private LocalDateTime orderDate;
    /**
     * 负责人ID
     */
    private String ownerUserId;
    /**
     * 合同编号
     */
    private String num;
    /**
     * 开始时间
     */
    private LocalDateTime startTime;
    /**
     * 结束时间
     */
    private LocalDateTime endTime;
    /**
     * 合同金额
     */
    private BigDecimal money;
    /**
     * 客户签约人（联系人id）
     */
    private String contactsId;
    /**
     * 公司签约人
     */
    private String companyUserId;
    /**
     * 备注
     */
    private String remark;
    /**
     * 整单折扣
     */
    private BigDecimal discountRate;
    /**
     * 产品总金额
     */
    private BigDecimal totalPrice;
    /**
     * 合同类型
     */
    private String types;
    /**
     * 付款方式
     */
    private String paymentType;
    /**
     * 最后跟进时间
     */
    private LocalDateTime lastTime;
    /**
     * 回款总金额
     */
    private BigDecimal receivedMoney;
    /**
     * 未回款总金额
     */
    private BigDecimal unreceivedMoney;
    /**
     * 未回款总金额-复制使用
     */
    private String oldBizId;

    @TableField(exist = false)
    @ApiModelProperty(value = "客户名称")
    private String memberUserName;

    @TableField(exist = false)
    @ApiModelProperty(value = "商机名称")
    private String businessName;

    @TableField(exist = false)
    @ApiModelProperty(value = "客户签约人名称")
    private String contactsName;

    @TableField(exist = false)
    @ApiModelProperty(value = "公司签约人名称")
    private String companyUserName;

    @TableField(exist = false)
    @ApiModelProperty(value = "合同类型")
    private String typesName;

    @TableField(exist = false)
    @ApiModelProperty(value = "负责人")
    private String ownerUserName;

    @TableField(exist = false)
    @ApiModelProperty(value = "创建人名称")
    private String creatorName;

    @TableField(exist = false)
    @ApiModelProperty(value = "负责人团队名称")
    private String ownerDeptName;

    @TableField(exist = false)
    @ApiModelProperty(value = "相关团队")
    private String teamMemberIds;

    @TableField(exist = false)
    @ApiModelProperty(value = "是否标星")
    private Boolean star;


}
