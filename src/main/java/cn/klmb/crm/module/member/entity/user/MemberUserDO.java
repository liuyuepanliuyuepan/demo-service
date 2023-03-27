package cn.klmb.crm.module.member.entity.user;

import cn.klmb.crm.framework.base.core.entity.KlmbBaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * 客户-用户 DO
 *
 * @author 超级管理员
 */
@TableName(value = "member_user", autoResultMap = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class MemberUserDO extends KlmbBaseDO {

    /**
     * 名称
     */
    private String name;
    /**
     * 联系方式
     */
    private String tel;
    /**
     * 客户级别 1大B，2小B，3C端 {@link cn.klmb.crm.module.member.enums.MemberUserLevelEnum}
     */
    private Integer level;
    /**
     * 地址
     */
    private String address;
    /**
     * 淘宝名
     */
    private String tbName;
    /**
     * 微信名
     */
    private String wxName;
    /**
     * 备注
     */
    private String remark;
    /**
     * 订单数量
     */
    private Integer orderCount;
    /**
     * 累计成交额（单位：分）
     */
    private Long tradeAmount;

}
