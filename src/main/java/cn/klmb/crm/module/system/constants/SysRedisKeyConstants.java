package cn.klmb.crm.module.system.constants;

import static cn.klmb.crm.framework.redis.core.RedisKeyDefine.KeyTypeEnum.STRING;

import cn.klmb.crm.framework.redis.core.RedisKeyDefine;
import cn.klmb.crm.module.system.entity.oauth2.SysOAuth2AccessTokenDO;
import java.time.Duration;

/**
 * System Redis Key 枚举类
 *
 * @author 快乐萌宝
 */
public interface SysRedisKeyConstants {

    RedisKeyDefine CAPTCHA_CODE = new RedisKeyDefine("验证码的缓存",
            "captcha_code:%s", // 参数为 uuid
            STRING, String.class, RedisKeyDefine.TimeoutTypeEnum.DYNAMIC);

    RedisKeyDefine OAUTH2_ACCESS_TOKEN = new RedisKeyDefine("访问令牌的缓存",
            "oauth2_access_token:%s", // 参数为访问令牌 token
            STRING, SysOAuth2AccessTokenDO.class, RedisKeyDefine.TimeoutTypeEnum.DYNAMIC);

    RedisKeyDefine SOCIAL_AUTH_STATE = new RedisKeyDefine("社交登陆的 state", // 注意，它是被 JustAuth 的 justauth.type.prefix 使用到
            "social_auth_state:%s", // 参数为 state
            STRING, String.class, Duration.ofHours(24)); // 值为 state


    // ======================= @Cacheable 使用的KEY =======================
    String SYS_USER_ROLE_USER_ID = "sys_user_role:uid";
    String SYS_ROLE = "sys_role";
    String SYS_ROLE_BIZ_ID = "sys_role:id";
    String SYS_MENU = "sys_menu";
    String SYS_MENU_PERMISSION = "sys_menu:p";
    String SYS_ROLE_MENU = "sys_role_menu";
    String SYS_ROLE_MENU_MENU_ID = "sys_role_menu:mid";

}
