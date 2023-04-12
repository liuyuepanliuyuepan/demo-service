package cn.klmb.crm.module.system.dto.feishu;

import lombok.Builder;
import lombok.Data;

/**
 * 飞书网页登录返回
 */
@Data
@Builder
public class FeishuWebResultDTO {

    /**
     * user_access_token，用于获取用户资源
     */
    private String access_token;

    /**
     * token 类型
     */
    private String token_type;

    /**
     * access_token 的有效期，单位: 秒
     */
    private Integer expires_in;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 用户英文名称
     */
    private String en_name;

    /**
     * 用户头像
     */
    private String avatar_url;


    /**
     * 用户头像 72x72
     */
    private String avatar_thumb;

    /**
     * 用户头像 240x240
     */
    private String avatar_middle;

    /**
     * 用户头像 640x640
     */
    private String avatar_big;


    /**
     * 用户在应用内的唯一标识
     */
    private String open_id;


    /**
     * 用户统一ID
     */
    private String union_id;


    /**
     * 用户邮箱
     */
    private String email;


    /**
     * 企业邮箱，请先确保已在管理后台启用飞书邮箱服务
     */
    private String enterprise_email;


    /**
     * 用户 user_id
     */
    private String user_id;


    /**
     * 用户手机号
     */
    private String mobile;


    /**
     * 当前企业标识
     */
    private String tenant_key;

    /**
     * refresh_token 的有效期，单位: 秒
     */
    private Integer refresh_expires_in;

    /**
     * 刷新用户 access_token 时使用的 token
     */
    private String refresh_token;


}
