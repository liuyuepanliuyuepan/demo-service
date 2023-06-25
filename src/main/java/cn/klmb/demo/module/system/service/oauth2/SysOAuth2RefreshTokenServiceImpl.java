package cn.klmb.demo.module.system.service.oauth2;

import cn.klmb.demo.framework.base.core.service.KlmbBaseServiceImpl;
import cn.klmb.demo.module.system.dao.oauth2.SysOAuth2RefreshTokenMapper;
import cn.klmb.demo.module.system.dto.oauth2.SysOAuth2RefreshTokenQueryDTO;
import cn.klmb.demo.module.system.entity.oauth2.SysOAuth2RefreshTokenDO;
import org.springframework.stereotype.Service;


/**
 * oauth2刷新令牌
 *
 * @author liuyuepan
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