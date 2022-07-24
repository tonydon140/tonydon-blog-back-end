package top.tonydon.config;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.ByteBuffer;

public class LongRedisSerializer implements RedisSerializer<Long> {
    @Override
    public byte[] serialize(Long aLong) throws SerializationException {
        if (aLong == null)
            return new byte[0];

        return ByteBuffer.allocate(8).putLong(aLong).array();
    }

    @Override
    public Long deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length != 8)
            return null;

        return ByteBuffer.allocate(8).put(bytes).flip().getLong();
    }
}
