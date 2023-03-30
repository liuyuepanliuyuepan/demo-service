package cn.klmb.crm.module.member.service.userstar;

import cn.klmb.crm.framework.base.core.service.KlmbBaseServiceImpl;
import cn.klmb.crm.module.member.dao.userstar.MemberUserStarMapper;
import cn.klmb.crm.module.member.dto.userstar.MemberUserStarQueryDTO;
import cn.klmb.crm.module.member.entity.userstar.MemberUserStarDO;
import org.springframework.stereotype.Service;


/**
 * 用户客户标星关系 Service 实现类
 *
 * @author 超级管理员
 */
@Service
public class MemberUserStarServiceImpl extends
        KlmbBaseServiceImpl<MemberUserStarDO, MemberUserStarQueryDTO, MemberUserStarMapper> implements
        MemberUserStarService {

    public MemberUserStarServiceImpl(MemberUserStarMapper mapper) {
        this.mapper = mapper;
    }

}
