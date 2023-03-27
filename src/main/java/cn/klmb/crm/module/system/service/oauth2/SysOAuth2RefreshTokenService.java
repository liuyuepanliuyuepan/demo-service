package cn.klmb.crm.module.system.service.oauth2;

import cn.klmb.crm.framework.base.core.service.KlmbBaseService;
import cn.klmb.crm.module.system.dto.oauth2.SysOAuth2RefreshTokenQueryDTO;
import cn.klmb.crm.module.system.entity.oauth2.SysOAuth2RefreshTokenDO;


/**
 * oauth2刷新令牌
 *
 * @author shilinchuan
 * @date 2022/12/1
 */
public interface SysOAuth2RefreshTokenService extends
        KlmbBaseService<SysOAuth2RefreshTokenDO, SysOAuth2RefreshTokenQueryDTO> {

}

