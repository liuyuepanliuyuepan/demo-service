package cn.klmb.demo.module.system.dao.logger;

import cn.klmb.demo.framework.base.core.dao.KlmbBaseMapper;
import cn.klmb.demo.module.system.dto.logger.SysLoginLogQueryDTO;
import cn.klmb.demo.module.system.entity.logger.SysLoginLogDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统管理-登录日志
 *
 * @author liuyuepan
 * @date 2022/12/12
 */
@Mapper
public interface SysLoginLogMapper extends KlmbBaseMapper<SysLoginLogDO, SysLoginLogQueryDTO> {

}
