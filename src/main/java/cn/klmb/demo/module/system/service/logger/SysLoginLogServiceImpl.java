package cn.klmb.demo.module.system.service.logger;

import cn.klmb.demo.framework.base.core.service.KlmbBaseServiceImpl;
import cn.klmb.demo.module.system.dao.logger.SysLoginLogMapper;
import cn.klmb.demo.module.system.dto.logger.SysLoginLogQueryDTO;
import cn.klmb.demo.module.system.entity.logger.SysLoginLogDO;
import org.springframework.stereotype.Service;


/**
 * 系统管理-登录日志
 *
 * @author liuyuepan
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