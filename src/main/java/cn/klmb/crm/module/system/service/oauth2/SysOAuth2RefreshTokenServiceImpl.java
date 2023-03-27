package cn.klmb.crm.module.system.service.oauth2;

import cn.klmb.crm.framework.base.core.service.KlmbBaseServiceImpl;
import cn.klmb.crm.module.system.dao.oauth2.SysOAuth2RefreshTokenMapper;
import cn.klmb.crm.module.system.dto.oauth2.SysOAuth2RefreshTokenQueryDTO;
import cn.klmb.crm.module.system.entity.oauth2.SysOAuth2RefreshTokenDO;
import org.springframework.stereotype.Service;


/**
 * oauth2刷新令牌
 *
 * @author shilinchuan
 * @date 2022/12/1
 */
@Service
public class SysOAuth2RefreshTokenServiceImpl extends
        KlmbBaseServiceImpl<SysOAuth2RefreshTokenDO, SysOAuth2RefreshTokenQueryDTO, SysOAuth2RefreshTokenMapper> implements
        SysOAuth2RefreshTokenService {

    public SysOAuth2RefreshTokenServiceImpl(SysOAuth2RefreshTokenMapper mapper) {
        this.mapper = mapper;
    }

}