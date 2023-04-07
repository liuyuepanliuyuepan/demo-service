package cn.klmb.crm.module.member.entity.teamactivity;

import cn.klmb.crm.framework.base.core.entity.KlmbBaseDO;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * crm团队成员活动 DO
 *
 * @author 超级管理员
 */
@TableName(value = "member_team_activity", autoResultMap = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class MemberTeamActivityDO extends KlmbBaseDO {

    /**
     * 活动类型 1 跟进记录 2 创建记录 3 商机阶段变更 4 外勤签到
     */
    private Integer type;
    /**
     * 跟进类型(例如打电话)
     */
    private String category;
    /**
     * 活动类型 1 线索 2 客户 3 联系人 4 产品 5 商机 6 合同 7回款 8日志 9审批 10日程 11任务 12 发邮件
     */
    private Integer activityType;
    /**
     * 活动类型Id
     */
    private String activityTypeId;
    /**
     * 活动内容
     */
    private String content;
    /**
     * 关联商机
     */
    private String businessIds;
    /**
     * 关联联系人
     */
    private String contactsIds;
    /**
     * 下次联系时间
     */
    private LocalDateTime nextTime;
    /**
     * 经度
     */
    private String lng;
    /**
     * 纬度
     */
    private String lat;
    /**
     * 签到地址
     */
    private String address;


    /**
     * 图片ids
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> imgIds;

    /**
     * 文件ids
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> fileIds;

}
