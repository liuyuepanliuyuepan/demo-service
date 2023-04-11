package cn.klmb.crm.module.member.service.record;


import cn.klmb.crm.framework.base.core.service.KlmbBaseServiceImpl;
import cn.klmb.crm.module.member.dao.record.MemberOwnerRecordMapper;
import cn.klmb.crm.module.member.dto.record.MemberOwnerRecordQueryDTO;
import cn.klmb.crm.module.member.entity.record.MemberOwnerRecordDO;
import org.springframework.stereotype.Service;


/**
 * 负责人变更记录 Service 实现类
 *
 * @author 超级管理员
 */
@Service
public class MemberOwnerRecordServiceImpl extends
        KlmbBaseServiceImpl<MemberOwnerRecordDO, MemberOwnerRecordQueryDTO, MemberOwnerRecordMapper> implements
        MemberOwnerRecordService {

    public MemberOwnerRecordServiceImpl(MemberOwnerRecordMapper mapper) {
        this.mapper = mapper;
    }

}
