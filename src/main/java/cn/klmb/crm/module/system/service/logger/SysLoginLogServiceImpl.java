package cn.klmb.crm.module.system.service.logger;

import cn.klmb.crm.framework.base.core.service.KlmbBaseServiceImpl;
import cn.klmb.crm.module.system.dao.logger.SysLoginLogMapper;
import cn.klmb.crm.module.system.dto.logger.SysLoginLogQueryDTO;
import cn.klmb.crm.module.system.entity.logger.SysLoginLogDO;
import org.springframework.stereotype.Service;


/**
 * 系统管理-登录日志
 *
 * @author shilinchuan
 * @date 2022/12/12
 */
@Service
public class SysLoginLogServiceImpl extends
        KlmbBaseServiceImpl<SysLoginLogDO, SysLoginLogQueryDTO, SysLoginLogMapper> implements
        SysLoginLogService {


    public SysLoginLogServiceImpl(SysLoginLogMapper mapper) {
        this.mapper = mapper;
    }

}