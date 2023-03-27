package cn.klmb.crm.module.system.service.oauth2;

import cn.klmb.crm.framework.base.core.service.KlmbBaseService;
import cn.klmb.crm.module.system.dto.oauth2.SysOAuth2ClientQueryDTO;
import cn.klmb.crm.module.system.entity.oauth2.SysOAuth2ClientDO;
import java.util.Collection;


/**
 * oauth2客户端
 *
 * @author shilinchuan
 * @date 2022/12/1
 */
public interface SysOAuth2ClientService extends
        KlmbBaseService<SysOAuth2ClientDO, SysOAuth2ClientQueryDTO> {

    /**
     * 校验客户端是否合法
     *
     * @return 客户端
     */
    default SysOAuth2ClientDO validOAuthClient(String clientId) {
        return validOAuthClient(clientId, null, null, null, null);
    }

    /**
     * 校验客户端是否合法
     *
     * 非空时，进行校验
     *
     * @param clientId 客户端编号
     * @param clientSecret 客户端密钥
     * @param authorizedGrantType 授权方式
     * @param scopes 授权范围
     * @param redirectUri 重定向地址
     * @return 客户端
     */
    SysOAuth2ClientDO validOAuthClient(String clientId, String clientSecret, String authorizedGrantType, Collection<String> scopes, String redirectUri);

}

