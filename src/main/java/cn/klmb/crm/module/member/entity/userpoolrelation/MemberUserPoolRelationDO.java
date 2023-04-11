package cn.klmb.crm.module.member.entity.userpoolrelation;


import cn.klmb.crm.framework.base.core.entity.KlmbBaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * 客户公海关联 DO
 *
 * @author 超级管理员
 */
@TableName(value = "member_user_pool_relation", autoResultMap = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class MemberUserPoolRelationDO extends KlmbBaseDO {

    /**
     * 客户id
     */
    private String customerId;
    /**
     * 公海id
     */
    private String poolId;

}
