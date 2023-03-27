package cn.klmb.crm.framework.mq.config;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.system.SystemUtil;
import cn.klmb.crm.framework.mq.core.RedisMQTemplate;
import cn.klmb.crm.framework.mq.core.interceptor.RedisMessageInterceptor;
import cn.klmb.crm.framework.mq.core.pubsub.AbstractChannelMessageListener;
import cn.klmb.crm.framework.redis.config.EduRedisAutoConfiguration;
import java.util.List;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/**
 * 消息队列配置类
 *
 * @author 快乐萌宝
 */
@Configuration
@AutoConfigureAfter(EduRedisAutoConfiguration.class)
@Slf4j
public class EduMQAutoConfiguration {

    /**
     * 构建消费者名字，使用本地 IP + 进程编号的方式。 参考自 RocketMQ clientId 的实现
     *
     * @return 消费者名字
     */
    private static String buildConsumerName() {
        return String.format("%s@%d", SystemUtil.getHostInfo().getAddress(),
                SystemUtil.getCurrentPID());
    }

    // ========== 消费者相关 ==========

    /**
     * 校验 Redis 版本号，是否满足最低的版本号要求！
     */
    private static void checkRedisVersion(RedisTemplate<String, ?> redisTemplate) {
        // 获得 Redis 版本
        Properties info = redisTemplate.execute(
                (RedisCallback<Properties>) RedisServerCommands::info);
        String version = MapUtil.getStr(info, "redis_version");
        // 校验最低版本必须大于等于 5.0.0
        int majorVersion = Integer.parseInt(StrUtil.subBefore(version, '.', false));
        if (majorVersion < 5) {
            throw new IllegalStateException(
                    StrUtil.format("您当前的 Redis 版本为 {}，小于最低要求的 5.0.0 版本！", version));
        }
    }

    @Bean
    public RedisMQTemplate redisMQTemplate(StringRedisTemplate redisTemplate,
            List<RedisMessageInterceptor> interceptors) {
        RedisMQTemplate redisMQTemplate = new RedisMQTemplate(redisTemplate);
        // 添加拦截器
        interceptors.forEach(redisMQTemplate::addInterceptor);
        return redisMQTemplate;
    }

    /**
     * 创建 Redis Pub/Sub 广播消费的容器
     */
    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(
            RedisMQTemplate redisMQTemplate, List<AbstractChannelMessageListener<?>> listeners) {
        // 创建 RedisMessageListenerContainer 对象
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        // 设置 RedisConnection 工厂。
        container.setConnectionFactory(
                redisMQTemplate.getRedisTemplate().getRequiredConnectionFactory());
        // 添加监听器
        listeners.forEach(listener -> {
            listener.setRedisMQTemplate(redisMQTemplate);
            container.addMessageListener(listener, new ChannelTopic(listener.getChannel()));
            log.info("[redisMessageListenerContainer][注册 Channel({}) 对应的监听器({})]",
                    listener.getChannel(), listener.getClass().getName());
        });
        return container;
    }

}
