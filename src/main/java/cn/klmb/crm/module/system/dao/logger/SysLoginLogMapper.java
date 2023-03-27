package cn.klmb.crm.module.system.dao.logger;

import cn.klmb.crm.framework.base.core.dao.KlmbBaseMapper;
import cn.klmb.crm.module.system.dto.logger.SysLoginLogQueryDTO;
import cn.klmb.crm.module.system.entity.logger.SysLoginLogDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统管理-登录日志
 *
 * @author shilinchuan
 * @date 2022/12/12
 */
@Mapper
public interface SysLoginLogMapper extends KlmbBaseMapper<SysLoginLogDO, SysLoginLogQueryDTO> {

}
