package cn.klmb.crm.module.member.entity.contacts;

import cn.klmb.crm.framework.base.core.entity.KlmbBaseDO;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * 联系人 DO
 *
 * @author 超级管理员
 */
@TableName(value = "member_contacts", autoResultMap = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class MemberContactsDO extends KlmbBaseDO {

    /**
     * 联系人名称
     */
    private String name;
    /**
     * 下次联系时间
     */
    private LocalDateTime nextTime;
    /**
     * 手机
     */
    private String mobile;
    /**
     * 电话
     */
    private String tel;
    /**
     * 电子邮箱
     */
    private String email;
    /**
     * 职务
     */
    private String post;
    /**
     * 客户id
     */
    private String customerId;
    /**
     * 地址
     */
    private String address;
    /**
     * 备注
     */
    private String remark;
    /**
     * 负责人ID
     */
    private String ownerUserId;
    /**
     * 最后跟进时间
     */
    private LocalDateTime lastTime;

    /**
     * 直属上级id
     */
    private String parentContactsId;

    /**
     * 联系人性别
     */
    private Integer sex;

    @TableField(exist = false)
    private String customerName;

    /**
     * 直属上级
     */
    @TableField(exist = false)
    private String parentContactsName;

    /**
     * 负责人
     */
    @TableField(exist = false)
    private String ownerUserName;


    /**
     * 是否首要联系人
     */
    @TableField(exist = false)
    private Boolean isFirstContacts;


    /**
     * 是否标星
     */
    @TableField(exist = false)
    private Boolean star;


}
