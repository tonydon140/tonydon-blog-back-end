package top.tonydon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> stringObjectRedisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);

        // key的序列化器
        template.setKeySerializer(RedisSerializer.string());
        template.setHashKeySerializer(RedisSerializer.string());

        // value的序列化器
        template.setValueSerializer(RedisSerializer.json());
        template.setHashValueSerializer(RedisSerializer.json());
        return template;
    }

    @Bean
    public RedisTemplate<String, Long> stringLongRedisTemplate(RedisConnectionFactory factory){
        RedisTemplate<String, Long> template = new RedisTemplate<>();
        LongRedisSerializer serializer = new LongRedisSerializer();
        template.setConnectionFactory(factory);

        // key的序列化器
        template.setKeySerializer(RedisSerializer.string());
        template.setHashKeySerializer(RedisSerializer.string());

        // value的序列化器
        template.setValueSerializer(serializer);
        template.setHashValueSerializer(serializer);
        return template;
    }

}
