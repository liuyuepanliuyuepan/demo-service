package cn.klmb.crm.module.member.service.userpool;


import cn.klmb.crm.framework.base.core.service.KlmbBaseServiceImpl;
import cn.klmb.crm.module.member.dao.userpool.MemberUserPoolMapper;
import cn.klmb.crm.module.member.dto.userpool.MemberUserPoolQueryDTO;
import cn.klmb.crm.module.member.entity.userpool.MemberUserPoolDO;
import org.springframework.stereotype.Service;


/**
 * 公海 Service 实现类
 *
 * @author 超级管理员
 */
@Service
public class MemberUserPoolServiceImpl extends
        KlmbBaseServiceImpl<MemberUserPoolDO, MemberUserPoolQueryDTO, MemberUserPoolMapper> implements
        MemberUserPoolService {

    public MemberUserPoolServiceImpl(MemberUserPoolMapper mapper) {
        this.mapper = mapper;
    }

}
