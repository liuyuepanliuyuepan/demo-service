package cn.klmb.demo.module.system.service.oauth2;

import cn.klmb.demo.framework.base.core.service.KlmbBaseService;
import cn.klmb.demo.module.system.dto.oauth2.SysOAuth2RefreshTokenQueryDTO;
import cn.klmb.demo.module.system.entity.oauth2.SysOAuth2RefreshTokenDO;


/**
 * oauth2刷新令牌
 *
 * @author liuyuepan
 * @date 2022/12/1
 */
public interface SysOAuth2RefreshTokenService extends
        KlmbBaseService<SysOAuth2RefreshTokenDO, SysOAuth2RefreshTokenQueryDTO> {

}

