package cn.klmb.crm.module.member.entity.userstar;

import cn.klmb.crm.framework.base.core.entity.KlmbBaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * 用户客户标星关系 DO
 *
 * @author 超级管理员
 */
@TableName(value = "member_user_star", autoResultMap = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class MemberUserStarDO extends KlmbBaseDO {

    /**
     * 客户id
     */
    private String customerId;
    /**
     * 用户id
     */
    private String userId;

}
