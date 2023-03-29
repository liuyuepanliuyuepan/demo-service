package cn.klmb.crm.module.member.service.user;

import cn.klmb.crm.framework.base.core.service.KlmbBaseServiceImpl;
import cn.klmb.crm.module.member.dao.user.MemberUserMapper;
import cn.klmb.crm.module.member.dto.user.MemberUserQueryDTO;
import cn.klmb.crm.module.member.entity.user.MemberUserDO;
import org.springframework.stereotype.Service;


/**
 * 客户-用户 Service 实现类
 *
 * @author 超级管理员
 */
@Service
public class MemberUserServiceImpl extends
        KlmbBaseServiceImpl<MemberUserDO, MemberUserQueryDTO, MemberUserMapper> implements
        MemberUserService {

    public MemberUserServiceImpl(MemberUserMapper mapper) {
        this.mapper = mapper;
    }

}
