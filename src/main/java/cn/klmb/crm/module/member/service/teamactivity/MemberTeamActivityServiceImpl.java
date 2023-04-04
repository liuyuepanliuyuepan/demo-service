package cn.klmb.crm.module.member.service.teamactivity;

import cn.klmb.crm.framework.base.core.service.KlmbBaseServiceImpl;
import cn.klmb.crm.module.member.dao.teamactivity.MemberTeamActivityMapper;
import cn.klmb.crm.module.member.dto.teamactivity.MemberTeamActivityQueryDTO;
import cn.klmb.crm.module.member.entity.teamactivity.MemberTeamActivityDO;
import org.springframework.stereotype.Service;


/**
 * crm团队成员活动 Service 实现类
 *
 * @author 超级管理员
 */
@Service
public class MemberTeamActivityServiceImpl extends
        KlmbBaseServiceImpl<MemberTeamActivityDO, MemberTeamActivityQueryDTO, MemberTeamActivityMapper> implements
        MemberTeamActivityService {

    public MemberTeamActivityServiceImpl(MemberTeamActivityMapper mapper) {
        this.mapper = mapper;
    }

}
