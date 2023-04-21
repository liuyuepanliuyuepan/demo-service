package cn.klmb.crm.module.member.service.teamactivity;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.klmb.crm.framework.base.core.service.KlmbBaseServiceImpl;
import cn.klmb.crm.module.contract.entity.detail.ContractDetailDO;
import cn.klmb.crm.module.contract.service.detail.ContractDetailService;
import cn.klmb.crm.module.member.dao.teamactivity.MemberTeamActivityMapper;
import cn.klmb.crm.module.member.dto.teamactivity.MemberTeamActivityQueryDTO;
import cn.klmb.crm.module.member.entity.teamactivity.MemberTeamActivityDO;
import cn.klmb.crm.module.member.entity.user.MemberUserDO;
import cn.klmb.crm.module.member.service.user.MemberUserService;
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
    private final ContractDetailService contractDetailService;
    public MemberTeamActivityServiceImpl(MemberUserService memberUserService,
            ContractDetailService contractDetailService, MemberTeamActivityMapper mapper) {
        this.memberUserService = memberUserService;
        this.contractDetailService = contractDetailService;
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
        if (StrUtil.isNotBlank(saveDO.getActivityTypeId())) {
            // activityTypeId todo 合同跟进日志修改
            int typeId = saveDO.getActivityType();
            switch (typeId) {
                case 2 :
                    // 客户
                    MemberUserDO memberUserDO = memberUserService.getByBizId(saveDO.getActivityTypeId());
                    if (ObjectUtil.isNotNull(memberUserDO)) {
                        memberUserDO.setLastContent(saveDO.getContent());
                        memberUserDO.setLastTime(LocalDateTime.now());
                        memberUserDO.setFollowup(1);
                        if (ObjectUtil.isNotNull(saveDO.getNextTime())) {
                            memberUserDO.setNextTime(saveDO.getNextTime());
                        }
                        memberUserService.updateDO(memberUserDO);
                    }
                    break;
                case 6 :
                    // 合同
                    if (ObjectUtil.isNotNull(saveDO.getNextTime())) {
                        ContractDetailDO contractDetailDO = contractDetailService.getByBizId(
                                saveDO.getActivityTypeId());
                        if (ObjectUtil.isNotNull(contractDetailDO)) {
                            contractDetailDO.setEndTime(saveDO.getNextTime());
                            contractDetailService.updateDO(contractDetailDO);
                        }
                    }
                    break;
                default:

                    break;
            }

        }
    }
}
