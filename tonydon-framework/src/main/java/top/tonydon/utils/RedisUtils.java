package top.tonydon.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {

    @Resource
    private StringRedisTemplate stringTemplate;

    @Resource
    private RedisTemplate<String, Object> objectTemplate;

    private final ObjectMapper mapper = new ObjectMapper();

    private final TimeUnit TIME_UNIT = TimeUnit.SECONDS;


    public void setObject(String key, Object object) {
        objectTemplate.opsForValue().set(key, object);
    }

    /**
     * 以完整 json 数据格式存储对象，会存储对象的 class 属性，
     * 并设置存活时间
     *
     * @param key     键
     * @param object  值
     * @param seconds 存活时间，单位秒
     */
    public void setObject(String key, Object object, long seconds) {
        objectTemplate.opsForValue().set(key, object, seconds, TimeUnit.SECONDS);
    }

    public Object getObject(String key) {
        return objectTemplate.opsForValue().get(key);
    }

    /**
     * 根据 key 删除数据
     *
     * @param key 键
     */
    public void removeObject(String key) {
        objectTemplate.delete(key);
    }


    /**
     * 将 Java 对象转为 json 数据
     */
    private String objToJson(Object obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }

    /**
     * 将 json 数据转为 Java 对象
     */
    private <T> T jsonToObj(String json, Class<T> clazz) throws JsonProcessingException {
        return mapper.readValue(json, clazz);
    }


    public void setObjectHash(String key, Object obj) {
        // 将对象转为 Map 集合
        Map<String, Object> objectMap = BeanUtil.beanToMap(
                obj,
                new HashMap<>(),
                CopyOptions.create()
                        .setIgnoreNullValue(true)
                        .setFieldValueEditor((fieldName, fieldValue) -> fieldValue.toString()));

//        for (String k : objectMap.keySet()) {
//            System.out.println("key = " + k + ", value = " + objectMap.get(k));
//        }
        // 存储到 Redis
        stringTemplate.opsForHash().putAll(key, objectMap);
    }

    /**
     * 将 Java 对象存储为 Hash 结果，并设置过期时间
     *
     * @param key     键
     * @param obj     值
     * @param seconds 过期时间，单位秒
     */
    public void setObjectHash(String key, Object obj, long seconds) {
        setObjectHash(key, obj);
        stringTemplate.expire(key, seconds, TimeUnit.SECONDS);
    }


    /**
     * 存储字符串数据
     *
     * @param key   键
     * @param value 值
     */
    public void setValue(String key, String value) {
        stringTemplate.opsForValue().set(key, value);
    }

    public void setValue(String key, String value, long seconds) {
        stringTemplate.opsForValue().set(key, value, seconds, TimeUnit.SECONDS);
    }


    /**
     * 获取字符串数据
     *
     * @param key 键
     * @return 值
     */
    public String getValue(String key) {
        return stringTemplate.opsForValue().get(key);
    }


    /**
     * 设置存活时间
     *
     * @param key     key
     * @param seconds 秒
     * @return 是否成功
     */
    public Boolean expire(String key, long seconds) {
        return stringTemplate.expire(key, seconds, TimeUnit.SECONDS);
    }

}
