package cn.klmb.crm.module.system.dto.feishu;

import lombok.Builder;
import lombok.Data;

/**
 * 飞书小程序登录返回
 */
@Data
@Builder
public class FeishuMinAppResultDTO {

    /**
     * 用户的Open ID，用于在同一个应用中对用户进行标识
     */
    private String open_id;


    /**
     * 用户的User ID，在职员工在企业内的唯一标识
     */
    private String employee_id;

    /**
     * 会话密钥
     */
    private String session_key;


    /**
     * 用户所在企业唯一标识
     */
    private String tenant_key;

    /**
     * user_access_token，用户身份访问凭证
     */
    private String access_token;


    /**
     * user_access_token过期时间戳
     */
    private Integer expires_in;

    /**
     * 刷新用户 access_token 时使用的 token，过期时间为30天。刷新access_token 接口说明请查看文档
     */
    private String refresh_token;


}
