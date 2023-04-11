package cn.klmb.crm.module.member.service.userpoolrelation;


import cn.klmb.crm.framework.base.core.service.KlmbBaseServiceImpl;
import cn.klmb.crm.module.member.dao.userpoolrelation.MemberUserPoolRelationMapper;
import cn.klmb.crm.module.member.dto.userpoolrelation.MemberUserPoolRelationQueryDTO;
import cn.klmb.crm.module.member.entity.userpoolrelation.MemberUserPoolRelationDO;
import org.springframework.stereotype.Service;


/**
 * 客户公海关联 Service 实现类
 *
 * @author 超级管理员
 */
@Service
public class MemberUserPoolRelationServiceImpl extends
        KlmbBaseServiceImpl<MemberUserPoolRelationDO, MemberUserPoolRelationQueryDTO, MemberUserPoolRelationMapper> implements
        MemberUserPoolRelationService {

    public MemberUserPoolRelationServiceImpl(MemberUserPoolRelationMapper mapper) {
        this.mapper = mapper;
    }

}
