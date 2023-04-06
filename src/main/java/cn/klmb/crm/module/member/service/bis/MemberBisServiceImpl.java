package cn.klmb.crm.module.member.service.bis;

import cn.klmb.crm.framework.base.core.service.KlmbBaseServiceImpl;
import cn.klmb.crm.module.member.dao.bis.MemberBisMapper;
import cn.klmb.crm.module.member.dto.bis.MemberBisQueryDTO;
import cn.klmb.crm.module.member.entity.bis.MemberBisDO;
import org.springframework.stereotype.Service;


/**
 * 客户工商信息 Service 实现类
 *
 * @author 超级管理员
 */
@Service
public class MemberBisServiceImpl extends
        KlmbBaseServiceImpl<MemberBisDO, MemberBisQueryDTO, MemberBisMapper> implements
        MemberBisService {

    public MemberBisServiceImpl(MemberBisMapper mapper) {
        this.mapper = mapper;
    }

}
