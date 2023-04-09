package cn.klmb.crm.module.member.entity.bis;

import cn.klmb.crm.framework.base.core.entity.KlmbBaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * 客户工商信息 DO
 *
 * @author 超级管理员
 */
@TableName(value = "member_bis", autoResultMap = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class MemberBisDO extends KlmbBaseDO {

    /**
     * 公司营业执照
     */
    private String img;
    /**
     * 办公地址
     */
    private String address;
    /**
     * 注册资本
     */
    private String registCapi;
    /**
     * 法定代表人
     */
    private String operName;
    /**
     * 统一社会信用代码
     */
    private String creditNo;
    /**
     * 电话
     */
    private String tel;
    /**
     * 客户id
     */
    private String customerId;


    /**
     * 发票类型(1普票，2专票)
     */
    private Integer ticketType;

    /**
     * 基本户
     */
    private String bba;

}
