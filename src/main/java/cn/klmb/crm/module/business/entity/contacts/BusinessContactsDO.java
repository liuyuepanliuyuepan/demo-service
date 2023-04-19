package cn.klmb.crm.module.business.entity.contacts;

import cn.klmb.crm.framework.base.core.entity.KlmbBaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * 商机联系人关联 DO
 *
 * @author 超级管理员
 */
@TableName(value = "business_contacts", autoResultMap = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class BusinessContactsDO extends KlmbBaseDO {

    /**
     * 商机
     */
    private String businessId;
    /**
     * 联系人
     */
    private String contactsId;

}
