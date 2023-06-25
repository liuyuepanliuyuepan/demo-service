package cn.klmb.demo.framework.mq.core.message;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;

/**
 * Redis 消息抽象基类
 *
 * @author 快乐萌宝
 */
@Data
public abstract class AbstractRedisMessage {

    /**
     * 头
     */
    private Map<String, String> headers = new HashMap<>();

    public String getHeader(String key) {
        return headers.get(key);
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

}
