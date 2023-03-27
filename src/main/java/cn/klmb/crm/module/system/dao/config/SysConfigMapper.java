package cn.klmb.crm.module.system.dao.config;

import cn.klmb.crm.framework.base.core.dao.KlmbBaseMapper;
import cn.klmb.crm.module.system.dto.config.SysConfigQueryDTO;
import cn.klmb.crm.module.system.entity.config.SysConfigDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统管理-配置管理
 *
 * @author shilinchuan
 * @date 2022/12/4
 */
@Mapper
public interface SysConfigMapper extends KlmbBaseMapper<SysConfigDO, SysConfigQueryDTO> {

    default SysConfigDO selectByConfigKey(String configKey) {
        return selectOne(SysConfigDO::getConfigKey, configKey);
    }

}
