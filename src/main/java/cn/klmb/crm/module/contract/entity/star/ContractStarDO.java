package cn.klmb.crm.module.contract.entity.star;

import cn.klmb.crm.framework.base.core.entity.KlmbBaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * 合同标星关系 DO
 *
 * @author 超级管理员
 */
@TableName(value = "contract_star", autoResultMap = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class ContractStarDO extends KlmbBaseDO {

    /**
     * 合同ID
     */
    private String contractId;
    /**
     * 用户id
     */
    private String userId;

}
