package cn.klmb.demo.module.system.service.oauth2;

import cn.klmb.demo.framework.base.core.service.KlmbBaseServiceImpl;
import cn.klmb.demo.module.system.dao.oauth2.SysOAuth2AccessTokenMapper;
import cn.klmb.demo.module.system.dto.oauth2.SysOAuth2AccessTokenQueryDTO;
import cn.klmb.demo.module.system.entity.oauth2.SysOAuth2AccessTokenDO;
import org.springframework.stereotype.Service;


/**
 * oauth2访问令牌
 *
 * @author liuyuepan
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