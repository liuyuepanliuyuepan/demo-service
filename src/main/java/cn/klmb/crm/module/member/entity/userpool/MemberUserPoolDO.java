package cn.klmb.crm.module.member.entity.userpool;


import cn.klmb.crm.framework.base.core.entity.KlmbBaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * 公海 DO
 *
 * @author 超级管理员
 */
@TableName(value = "member_user_pool", autoResultMap = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class MemberUserPoolDO extends KlmbBaseDO {

    /**
     * 公海名称
     */
    private String poolName;

}
