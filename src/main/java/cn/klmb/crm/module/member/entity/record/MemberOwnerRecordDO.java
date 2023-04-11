package cn.klmb.crm.module.member.entity.record;

import cn.klmb.crm.framework.base.core.entity.KlmbBaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * 负责人变更记录 DO
 *
 * @author 超级管理员
 */
@TableName(value = "member_owner_record", autoResultMap = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class MemberOwnerRecordDO extends KlmbBaseDO {

    /**
     * 对象id
     */
    private String typeId;
    /**
     * 对象类型
     */
    private Integer type;
    /**
     * 上一负责人
     */
    private String preOwnerUserId;
    /**
     * 接手负责人
     */
    private String postOwnerUserId;

}
