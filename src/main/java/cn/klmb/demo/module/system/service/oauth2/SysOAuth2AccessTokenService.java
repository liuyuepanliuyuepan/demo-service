package cn.klmb.demo.module.system.service.oauth2;

import cn.klmb.demo.framework.base.core.service.KlmbBaseService;
import cn.klmb.demo.module.system.dto.oauth2.SysOAuth2AccessTokenQueryDTO;
import cn.klmb.demo.module.system.entity.oauth2.SysOAuth2AccessTokenDO;


/**
 * oauth2访问令牌
 *
 * @author liuyuepan
 * @date 2022/11/30
 */
public interface SysOAuth2AccessTokenService extends
        KlmbBaseService<SysOAuth2AccessTokenDO, SysOAuth2AccessTokenQueryDTO> {

}

