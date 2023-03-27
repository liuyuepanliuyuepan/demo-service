package cn.klmb.crm.framework.mq.core;

import cn.klmb.crm.framework.common.util.json.JsonUtils;
import cn.klmb.crm.framework.mq.core.interceptor.RedisMessageInterceptor;
import cn.klmb.crm.framework.mq.core.message.AbstractRedisMessage;
import cn.klmb.crm.framework.mq.core.pubsub.AbstractChannelMessage;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Redis MQ 操作模板类
 *
 * @author 快乐萌宝
 */
@AllArgsConstructor
public class RedisMQTemplate {

    @Getter
    private final RedisTemplate<String, ?> redisTemplate;
    /**
     * 拦截器数组
     */
    @Getter
    private final List<RedisMessageInterceptor> interceptors = new ArrayList<>();

    /**
     * 发送 Redis 消息，基于 Redis pub/sub 实现
     *
     * @param message 消息
     */
    public <T extends AbstractChannelMessage> void send(T message) {
        try {
            sendMessageBefore(message);
            // 发送消息
            redisTemplate.convertAndSend(message.getChannel(), JsonUtils.toJsonString(message));
        } finally {
            sendMessageAfter(message);
        }
    }

    /**
     * 添加拦截器
     *
     * @param interceptor 拦截器
     */
    public void addInterceptor(RedisMessageInterceptor interceptor) {
        interceptors.add(interceptor);
    }

    private void sendMessageBefore(AbstractRedisMessage message) {
        // 正序
        interceptors.forEach(interceptor -> interceptor.sendMessageBefore(message));
    }

    private void sendMessageAfter(AbstractRedisMessage message) {
        // 倒序
        for (int i = interceptors.size() - 1; i >= 0; i--) {
            interceptors.get(i).sendMessageAfter(message);
        }
    }

}
