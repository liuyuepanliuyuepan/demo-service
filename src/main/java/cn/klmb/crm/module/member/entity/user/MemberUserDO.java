package cn.klmb.crm.module.member.entity.user;

import cn.klmb.crm.framework.base.core.entity.KlmbBaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
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
     * 客户名称
     */
    private String name;
    /**
     * 客户级别 1(重点客户)，2(普通用户)，3(非优先客户)
     */
    private Integer level;
    /**
     * 客户来源
     */
    private Integer source;
    /**
     * 下次联系时间
     */
    private LocalDateTime nextTime;
    /**
     * 成交状态 0 未成交 1 已成交
     */
    private Integer dealStatus;
    /**
     * 成交时间
     */
    private LocalDateTime dealTime;
    /**
     * 首要联系人ID
     */
    private String contactsId;
    /**
     * 手机
     */
    private String mobile;
    /**
     * 电话
     */
    private String tel;
    /**
     * 网址
     */
    private String website;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 备注
     */
    private String remark;
    /**
     * 负责人ID
     */
    private String ownerUserId;
    /**
     * 省市区
     */
    private String address;
    /**
     * 定位信息
     */
    private String location;
    /**
     * 详细地址
     */
    private String detailAddress;
    /**
     * 地理位置经度
     */
    private String lng;
    /**
     * 地理位置纬度
     */
    private String lat;
    /**
     * 最后跟进时间
     */
    private LocalDateTime lastTime;
    /**
     * 放入公海时间
     */
    private LocalDateTime poolTime;
    /**
     * 1 分配 2 领取
     */
    private Integer isReceive;
    /**
     * 最后一条跟进记录
     */
    private String lastContent;
    /**
     * 跟进状态(0未跟进1已跟进)
     */
    private Integer followup;
    /**
     * 上级客户id
     */
    private String pid;

    /**
     * 客户行业
     */
    private Integer industry;

}
