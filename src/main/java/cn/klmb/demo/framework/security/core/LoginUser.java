package cn.klmb.demo.framework.security.core;

import cn.hutool.core.map.MapUtil;
import cn.klmb.demo.framework.common.enums.UserTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Data;

/**
 * 登录用户信息
 *
 * @author 快乐萌宝
 */
@Data
public class LoginUser {

    /**
     * 用户编号
     */
    private String id;
    /**
     * 用户类型
     * <p>
     * 关联 {@link UserTypeEnum}
     */
    private Integer userType;
    /**
     * 授权范围
     */
    private List<String> scopes;

    // ========== 上下文 ==========
    /**
     * 上下文字段，不进行持久化
     * <p>
     * 1. 用于基于 LoginUser 维度的临时缓存
     */
    @JsonIgnore
    private Map<String, Object> context;

    public void setContext(String key, Object value) {
        if (context == null) {
            context = new HashMap<>();
        }
        context.put(key, value);
    }

    public <T> T getContext(String key, Class<T> type) {
        return MapUtil.get(context, key, type);
    }

}
