package cn.klmb.crm.module.member.service.teamactivity;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.klmb.crm.framework.base.core.service.KlmbBaseServiceImpl;
import cn.klmb.crm.framework.job.dto.XxlJobChangeTaskDTO;
import cn.klmb.crm.framework.job.util.XxlJobApiUtils;
import cn.klmb.crm.module.business.entity.detail.BusinessDetailDO;
import cn.klmb.crm.module.business.service.detail.BusinessDetailService;
import cn.klmb.crm.module.member.dao.teamactivity.MemberTeamActivityMapper;
import cn.klmb.crm.module.member.dto.teamactivity.MemberTeamActivityQueryDTO;
import cn.klmb.crm.module.member.entity.teamactivity.MemberTeamActivityDO;
import cn.klmb.crm.module.member.entity.user.MemberUserDO;
import cn.klmb.crm.module.member.service.user.MemberUserService;
import cn.klmb.crm.module.system.enums.CrmEnum;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * crm团队成员活动 Service 实现类
 *
 * @author 超级管理员
 */
@Service
public class MemberTeamActivityServiceImpl extends
        KlmbBaseServiceImpl<MemberTeamActivityDO, MemberTeamActivityQueryDTO, MemberTeamActivityMapper> implements
        MemberTeamActivityService {

    private final MemberUserService memberUserService;

    private final BusinessDetailService businessDetailService;

    private final XxlJobApiUtils xxlJobApiUtils;

    public MemberTeamActivityServiceImpl(MemberUserService memberUserService,
            BusinessDetailService businessDetailService, MemberTeamActivityMapper mapper,
            XxlJobApiUtils xxlJobApiUtils) {
        this.memberUserService = memberUserService;
        this.businessDetailService = businessDetailService;
        this.xxlJobApiUtils = xxlJobApiUtils;
        this.mapper = mapper;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public String saveActivityDO(MemberTeamActivityDO saveDO) {
        String bizId = "";
        if (super.saveDO(saveDO)) {
            bizId = saveDO.getBizId();
        }
        updateLastContent(saveDO);
        return bizId;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateDO(MemberTeamActivityDO entity) {
        boolean b = super.updateDO(entity);
        updateLastContent(entity);
        return b;

    }

    private void updateLastContent(MemberTeamActivityDO saveDO) {
        switch (saveDO.getActivityType()) {
            case 2: {
                if (StrUtil.isNotBlank(saveDO.getActivityTypeId())) {
                    MemberUserDO memberUserDO = MemberUserDO.builder()
                            .bizId(saveDO.getActivityTypeId())
                            .lastContent(saveDO.getContent()).lastTime(LocalDateTime.now())
                            .followup(1)
                            .build();
                    if (ObjectUtil.isNotNull(saveDO.getNextTime())) {
                        memberUserDO.setNextTime(saveDO.getNextTime());
                    }
                    memberUserService.updateDO(memberUserDO);
                }
                break;
            }
            case 5: {
                if (StrUtil.isNotBlank(saveDO.getActivityTypeId())) {
                    BusinessDetailDO businessDetailDO = BusinessDetailDO.builder()
                            .bizId(saveDO.getActivityTypeId())
                            .lastTime(LocalDateTime.now()).followup(1)
                            .build();
                    if (ObjectUtil.isNotNull(saveDO.getNextTime())) {
                        businessDetailDO.setNextTime(saveDO.getNextTime());
                        BusinessDetailDO detailDO = businessDetailService.getByBizId(
                                saveDO.getActivityTypeId());
                        if (LocalDateTimeUtil.toEpochMilli(saveDO.getNextTime()) != 0
                                && !detailDO.getNextTime().isEqual(saveDO.getNextTime())) {
                            xxlJobApiUtils.changeTask(
                                    XxlJobChangeTaskDTO.builder().appName("xxl-job-executor-crm")
                                            .title("crm执行器")
                                            .executorHandler("customerContactReminderHandler")
                                            .author("liuyuepan")
                                            .ownerUserId(detailDO.getOwnerUserId())
                                            .bizId(detailDO.getBizId())
                                            .nextTime(saveDO.getNextTime())
                                            .name(detailDO.getBusinessName()).operateType(2)
                                            .messageType(CrmEnum.BUSINESS.getRemarks())
                                            .contactsType(CrmEnum.BUSINESS.getType())
                                            .build());
                        }
                    }
                    if (ObjectUtil.isNull(saveDO.getNextTime())
                            || LocalDateTimeUtil.toEpochMilli(saveDO.getNextTime()) == 0) {
                        xxlJobApiUtils.changeTask(
                                XxlJobChangeTaskDTO.builder().appName("xxl-job-executor-crm")
                                        .title("crm执行器")
                                        .executorHandler("customerContactReminderHandler")
                                        .author("liuyuepan")
                                        .bizId(saveDO.getActivityTypeId()).operateType(3)
                                        .messageType(CrmEnum.BUSINESS.getRemarks())
                                        .contactsType(CrmEnum.BUSINESS.getType()).build());
                    }
                    businessDetailService.updateDO(businessDetailDO);
                }
                break;
            }
            default:
        }
    }
}
