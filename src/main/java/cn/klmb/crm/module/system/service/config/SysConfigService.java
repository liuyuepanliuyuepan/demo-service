package cn.klmb.crm.module.system.service.config;

import cn.klmb.crm.framework.base.core.service.KlmbBaseService;
import cn.klmb.crm.module.system.dto.config.SysConfigQueryDTO;
import cn.klmb.crm.module.system.entity.config.SysConfigDO;


/**
 * 系统管理-配置管理
 *
 * @author shilinchuan
 * @date 2022/12/4
 */
public interface SysConfigService extends KlmbBaseService<SysConfigDO, SysConfigQueryDTO> {

    SysConfigDO getByConfigKey(String configKey);

}

