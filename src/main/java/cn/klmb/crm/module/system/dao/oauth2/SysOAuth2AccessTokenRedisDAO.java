package cn.klmb.crm.module.system.dao.oauth2;


import static cn.klmb.crm.module.system.constants.SysRedisKeyConstants.OAUTH2_ACCESS_TOKEN;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.klmb.crm.framework.common.util.collection.CollectionUtils;
import cn.klmb.crm.framework.common.util.json.JsonUtils;
import cn.klmb.crm.module.system.entity.oauth2.SysOAuth2AccessTokenDO;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

/**
 * {@link SysOAuth2AccessTokenDO} 的 RedisDAO
 *
 * @author 快乐萌宝
 */
@Repository
public class SysOAuth2AccessTokenRedisDAO {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public SysOAuth2AccessTokenDO get(String accessToken) {
        String redisKey = formatKey(accessToken);
        return JsonUtils.parseObject(stringRedisTemplate.opsForValue().get(redisKey),
                SysOAuth2AccessTokenDO.class);
    }

    public void set(SysOAuth2AccessTokenDO accessTokenDO) {
        String redisKey = formatKey(accessTokenDO.getAccessToken());
        // 清理多余字段，避免缓存
        accessTokenDO.setUpdater(null).setUpdateTime(null).setCreateTime(null).setCreator(null)
                .setDeleted(null);
        long time = LocalDateTimeUtil.between(LocalDateTime.now(), accessTokenDO.getExpiresTime(),
                ChronoUnit.SECONDS);
        stringRedisTemplate.opsForValue()
                .set(redisKey, JsonUtils.toJsonString(accessTokenDO), time, TimeUnit.SECONDS);
    }

    public void delete(String accessToken) {
        String redisKey = formatKey(accessToken);
        stringRedisTemplate.delete(redisKey);
    }

    public void deleteList(Collection<String> accessTokens) {
        List<String> redisKeys = CollectionUtils.convertList(accessTokens,
                SysOAuth2AccessTokenRedisDAO::formatKey);
        stringRedisTemplate.delete(redisKeys);
    }

    private static String formatKey(String accessToken) {
        return String.format(OAUTH2_ACCESS_TOKEN.getKeyTemplate(), accessToken);
    }

}
