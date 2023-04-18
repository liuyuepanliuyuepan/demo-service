package cn.klmb.crm.module.business.entity.userstar;

import cn.klmb.crm.framework.base.core.entity.KlmbBaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * 用户商机标星关系 DO
 *
 * @author 超级管理员
 */
@TableName(value = "business_user_star", autoResultMap = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class BusinessUserStarDO extends KlmbBaseDO {

    /**
     * 用户id
     */
    private String userId;
    /**
     * 客户id
     */
    private String businessId;

}
