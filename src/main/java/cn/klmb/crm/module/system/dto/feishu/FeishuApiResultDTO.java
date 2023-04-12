package cn.klmb.crm.module.system.dto.feishu;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * 飞书登录返回
 */
@Data
@Builder
public class FeishuApiResultDTO {

    /**
     * 用户的Open ID，用于在同一个应用中对用户进行标识
     */
    @JsonProperty(value = "open_id")
    private String openId;


    /**
     * 用户的User ID，在职员工在企业内的唯一标识
     */
    @JsonProperty(value = "employee_id")
    private String employeeId;

    /**
     * 会话密钥
     */
    @JsonProperty(value = "session_key")
    private String sessionKey;


    /**
     * 用户所在企业唯一标识
     */
    @JsonProperty(value = "tenant_key")
    private String tenantKey;

    /**
     * user_access_token，用户身份访问凭证
     */
    @JsonProperty(value = "access_token")
    private String accessToken;


    /**
     * user_access_token过期时间戳
     */
    @JsonProperty(value = " expires_in")
    private Integer expiresIn;

    /**
     * 刷新用户 access_token 时使用的 token，过期时间为30天。刷新access_token 接口说明请查看文档
     */
    @JsonProperty(value = "refresh_token")
    private String refreshToken;


}
