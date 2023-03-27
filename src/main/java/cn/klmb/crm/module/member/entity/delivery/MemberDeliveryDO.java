package cn.klmb.crm.module.member.entity.delivery;

import cn.klmb.crm.framework.base.core.entity.KlmbBaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * 客户-收货地址 DO
 *
 * @author 超级管理员
 */
@TableName(value = "member_delivery", autoResultMap = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class MemberDeliveryDO extends KlmbBaseDO {

    /**
     * 客户ID
     */
    private String memberUserId;
    /**
     * 收件人信息
     */
    private String name;
    /**
     * 收件人电话
     */
    private String tel;
    /**
     * 收货地址
     */
    private String address;
    /**
     * 备注
     */
    private String remark;

}
