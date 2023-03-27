package cn.klmb.crm.module.system.service.oauth2;

import cn.klmb.crm.framework.base.core.service.KlmbBaseServiceImpl;
import cn.klmb.crm.module.system.dao.oauth2.SysOAuth2AccessTokenMapper;
import cn.klmb.crm.module.system.dto.oauth2.SysOAuth2AccessTokenQueryDTO;
import cn.klmb.crm.module.system.entity.oauth2.SysOAuth2AccessTokenDO;
import org.springframework.stereotype.Service;


/**
 * oauth2访问令牌
 *
 * @author shilinchuan
 * @date 2022/12/1
 */
@Service
public class SysOAuth2AccessTokenServiceImpl extends
        KlmbBaseServiceImpl<SysOAuth2AccessTokenDO, SysOAuth2AccessTokenQueryDTO, SysOAuth2AccessTokenMapper> implements
        SysOAuth2AccessTokenService {

    public SysOAuth2AccessTokenServiceImpl(SysOAuth2AccessTokenMapper mapper) {
        this.mapper = mapper;
    }

}