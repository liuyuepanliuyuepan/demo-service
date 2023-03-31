package cn.klmb.crm.module.member.entity.contactsstar;

import cn.klmb.crm.framework.base.core.entity.KlmbBaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * 用户联系人标星关系 DO
 *
 * @author 超级管理员
 */
@TableName(value = "member_contacts_star", autoResultMap = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class MemberContactsStarDO extends KlmbBaseDO {

    /**
     * 联系人id
     */
    private String contactsId;
    /**
     * 用户id
     */
    private String userId;

}
