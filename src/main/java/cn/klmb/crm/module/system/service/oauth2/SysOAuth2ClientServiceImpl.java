package cn.klmb.crm.module.system.service.oauth2;

import static cn.klmb.crm.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.klmb.crm.module.system.enums.ErrorCodeConstants.OAUTH2_CLIENT_AUTHORIZED_GRANT_TYPE_NOT_EXISTS;
import static cn.klmb.crm.module.system.enums.ErrorCodeConstants.OAUTH2_CLIENT_CLIENT_SECRET_ERROR;
import static cn.klmb.crm.module.system.enums.ErrorCodeConstants.OAUTH2_CLIENT_DISABLE;
import static cn.klmb.crm.module.system.enums.ErrorCodeConstants.OAUTH2_CLIENT_NOT_EXISTS;
import static cn.klmb.crm.module.system.enums.ErrorCodeConstants.OAUTH2_CLIENT_REDIRECT_URI_NOT_MATCH;
import static cn.klmb.crm.module.system.enums.ErrorCodeConstants.OAUTH2_CLIENT_SCOPE_OVER;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.klmb.crm.framework.base.core.service.KlmbBaseServiceImpl;
import cn.klmb.crm.framework.common.enums.CommonStatusEnum;
import cn.klmb.crm.framework.common.util.string.StrUtils;
import cn.klmb.crm.module.system.dao.oauth2.SysOAuth2ClientMapper;
import cn.klmb.crm.module.system.dto.oauth2.SysOAuth2ClientQueryDTO;
import cn.klmb.crm.module.system.entity.oauth2.SysOAuth2ClientDO;
import java.util.Collection;
import org.springframework.stereotype.Service;


/**
 * oauth2客户端
 *
 * @author shilinchuan
 * @date 2022/12/1
 */
@Service
public class SysOAuth2ClientServiceImpl extends
        KlmbBaseServiceImpl<SysOAuth2ClientDO, SysOAuth2ClientQueryDTO, SysOAuth2ClientMapper> implements
        SysOAuth2ClientService {

    public SysOAuth2ClientServiceImpl(SysOAuth2ClientMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public SysOAuth2ClientDO validOAuthClient(String clientId, String clientSecret,
            String authorizedGrantType, Collection<String> scopes, String redirectUri) {
        // 校验客户端存在、且开启
        SysOAuth2ClientDO client = mapper.selectByClientId(clientId);
        if (client == null) {
            throw exception(OAUTH2_CLIENT_NOT_EXISTS);
        }
        if (ObjectUtil.notEqual(client.getStatus(), CommonStatusEnum.ENABLE.getStatus())) {
            throw exception(OAUTH2_CLIENT_DISABLE);
        }

        // 校验客户端密钥
        if (StrUtil.isNotEmpty(clientSecret) && ObjectUtil.notEqual(client.getSecret(), clientSecret)) {
            throw exception(OAUTH2_CLIENT_CLIENT_SECRET_ERROR);
        }
        // 校验授权方式
        if (StrUtil.isNotEmpty(authorizedGrantType) && !CollUtil.contains(client.getAuthorizedGrantTypes(), authorizedGrantType)) {
            throw exception(OAUTH2_CLIENT_AUTHORIZED_GRANT_TYPE_NOT_EXISTS);
        }
        // 校验授权范围
        if (CollUtil.isNotEmpty(scopes) && !CollUtil.containsAll(client.getScopes(), scopes)) {
            throw exception(OAUTH2_CLIENT_SCOPE_OVER);
        }
        // 校验回调地址
        if (StrUtil.isNotEmpty(redirectUri) && !StrUtils.startWithAny(redirectUri, client.getRedirectUris())) {
            throw exception(OAUTH2_CLIENT_REDIRECT_URI_NOT_MATCH, redirectUri);
        }
        return client;
    }
}