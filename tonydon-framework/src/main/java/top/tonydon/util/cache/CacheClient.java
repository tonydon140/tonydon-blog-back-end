package top.tonydon.util.cache;

import com.alibaba.fastjson2.JSON;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import top.tonydon.constant.RedisConstants;
import top.tonydon.domain.entity.RedisData;
import top.tonydon.util.function.ListFunction;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class CacheClient {

    private final StringRedisTemplate template;

    public CacheClient(StringRedisTemplate template) {
        this.template = template;
    }

    public void set(String key, Object value, Long time, TimeUnit unit) {
        template.opsForValue().set(key, JSON.toJSONString(value), time, unit);
    }

    // 设置逻辑过期时间
    public void setWithLogicalExpire(String key, Object value, Long time, TimeUnit unit) {
        RedisData data = new RedisData();
        data.setData(value);
        data.setExpireTime(LocalDateTime.now().plusSeconds(unit.toSeconds(time)));
        template.opsForValue().set(key, JSON.toJSONString(data));
    }

    /**
     * 从缓存中查询 key，若数据不在缓存中，则从get中获取。适合不怎么变化的查询
     * 该方法通过缓存空值的方法解决缓存穿透问题
     *
     * @param key  key
     * @param type 类型
     * @param get  数据库操作
     * @param time 时间长度
     * @param unit 时间单位
     * @param <R>  R
     * @return R
     */
    public <R> List<R> getWithPenetration(String key, Class<R> type, ListFunction<R> get, Long time, TimeUnit unit) {
        // 1. 从 Redis 查询缓存，如果Redis未命中则返回null
        String json = template.opsForValue().get(key);

        // 2. 判断是否存在，如果存在直接返回
        if (StringUtils.hasText(json)) {
            return JSON.parseArray(json, type);
        }

        // 3. 判断命中的是否是空值
        if (json != null) return null;

        // 4. 不存在，查询数据库
        List<R> r = get.apply();

        // 5. 写入Redis，返回
        set(key, r, time, unit);
        return r;
    }

    /**
     * 根据key从缓存中查询值，若数据不存在缓存中，则通过 queryById 回调查询数据库
     * 该方法通过缓存空值的方法解决缓存穿透问题
     */
    public <T, ID> T getWithPenetration(String keyPrefix, ID id, Class<T> type, Function<ID, T> queryById, Long time, TimeUnit unit) {
        // 1. 从 Redis 查询缓存，如果Redis未命中则返回null
        String key = keyPrefix + id;
        String json = template.opsForValue().get(key);

        // 2. 判断是否存在，如果存在直接返回
        if (StringUtils.hasText(json)) {
            return JSON.parseObject(json, type);
        }

        // 3. 判断命中的是否是空值
        if (json != null) {
            return null;
        }

        // 4. 不存在，根据id查询数据库
        T t = queryById.apply(id);

        // 5. 不存在，返回error
        if (t == null) {
            // 缓存空值
            template.opsForValue().set(key, "", RedisConstants.CACHE_NULL_TTL, TimeUnit.MINUTES);
            return null;
        }

        // 6. 存在，写入Redis，返回
        set(key, t, time, unit);
        return t;
    }

}
