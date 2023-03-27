package cn.klmb.crm.module.system.service.sms;

import cn.klmb.crm.framework.base.core.service.KlmbBaseServiceImpl;
import cn.klmb.crm.module.system.dao.sms.SysSmsCodeMapper;
import cn.klmb.crm.module.system.dto.sms.SysSmsCodeQueryDTO;
import cn.klmb.crm.module.system.entity.sms.SysSmsCodeDO;
import org.springframework.stereotype.Service;


/**
 * 系统管理-短信验证码
 *
 * @author shilinchuan
 * @date 2022/12/8
 */
@Service
public class SysSmsCodeServiceImpl extends
        KlmbBaseServiceImpl<SysSmsCodeDO, SysSmsCodeQueryDTO, SysSmsCodeMapper> implements
        SysSmsCodeService {


    public SysSmsCodeServiceImpl(SysSmsCodeMapper mapper) {
        this.mapper = mapper;
    }

}