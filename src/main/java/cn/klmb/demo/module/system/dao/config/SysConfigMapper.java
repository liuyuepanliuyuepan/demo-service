package cn.klmb.demo.module.system.dao.config;

import cn.klmb.demo.framework.base.core.dao.KlmbBaseMapper;
import cn.klmb.demo.module.system.dto.config.SysConfigQueryDTO;
import cn.klmb.demo.module.system.entity.config.SysConfigDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统管理-配置管理
 *
 * @author liuyuepan
 * @date 2022/12/4
 */
@Mapper
public interface SysConfigMapper extends KlmbBaseMapper<SysConfigDO, SysConfigQueryDTO> {

    default SysConfigDO selectByConfigKey(String configKey) {
        return selectOne(SysConfigDO::getConfigKey, configKey);
    }

}
