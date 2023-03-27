package cn.klmb.crm.module.system.enums.oauth2;

import cn.hutool.core.util.ArrayUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * OAuth2 授权类型（模式）的枚举
 *
 * @author 快乐萌宝
 */
@AllArgsConstructor
@Getter
public enum SysOAuth2GrantTypeEnum {

    PASSWORD("password"), // 密码模式
    AUTHORIZATION_CODE("authorization_code"), // 授权码模式
    IMPLICIT("implicit"), // 简化模式
    CLIENT_CREDENTIALS("client_credentials"), // 客户端模式
    REFRESH_TOKEN("refresh_token"), // 刷新模式
    ;

    private final String grantType;

    public static SysOAuth2GrantTypeEnum getByGranType(String grantType) {
        return ArrayUtil.firstMatch(o -> o.getGrantType().equals(grantType), values());
    }

}
