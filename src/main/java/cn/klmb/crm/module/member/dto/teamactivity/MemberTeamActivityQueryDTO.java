package cn.klmb.crm.module.member.dto.teamactivity;

import cn.klmb.crm.framework.base.core.annotations.DtoFieldQuery;
import cn.klmb.crm.framework.base.core.annotations.DtoFieldQuery.Operator;
import cn.klmb.crm.framework.base.core.pojo.KlmbBaseQueryDTO;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * crm团队成员活动 DO
 *
 * @author 超级管理员
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class MemberTeamActivityQueryDTO extends KlmbBaseQueryDTO {

    /**
     * 活动类型 1 跟进记录 2 创建记录 3 商机阶段变更 4 外勤签到
     */
    @DtoFieldQuery
    private Integer type;
    /**
     * 跟进类型(例如打电话)
     */
    @DtoFieldQuery
    private String category;
    /**
     * 活动类型 1 线索 2 客户 3 联系人 4 产品 5 商机 6 合同 7回款 8日志 9审批 10日程 11任务 12 发邮件
     */
    @DtoFieldQuery
    private Integer activityType;
    /**
     * 活动类型Id
     */
    @DtoFieldQuery
    private String activityTypeId;
    /**
     * 活动内容
     */
    @DtoFieldQuery
    private String content;
    /**
     * 关联商机
     */
    @DtoFieldQuery
    private String businessIds;
    /**
     * 关联联系人
     */
    @DtoFieldQuery
    private String contactsIds;
    /**
     * 下次联系时间
     */
    @DtoFieldQuery(queryType = Operator.BETWEEN)
    private LocalDateTime nextTime;
    /**
     * 经度
     */
    @DtoFieldQuery
    private String lng;
    /**
     * 纬度
     */
    @DtoFieldQuery
    private String lat;
    /**
     * 签到地址
     */
    @DtoFieldQuery
    private String address;

}
