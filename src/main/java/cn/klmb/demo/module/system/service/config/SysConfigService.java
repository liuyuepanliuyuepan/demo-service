package cn.klmb.demo.module.system.service.config;

import cn.klmb.demo.framework.base.core.service.KlmbBaseService;
import cn.klmb.demo.module.system.dto.config.SysConfigQueryDTO;
import cn.klmb.demo.module.system.entity.config.SysConfigDO;


/**
 * 系统管理-配置管理
 *
 * @author liuyuepan
 * @date 2022/12/4
 */
public interface SysConfigService extends KlmbBaseService<SysConfigDO, SysConfigQueryDTO> {

    SysConfigDO getByConfigKey(String configKey);

}

