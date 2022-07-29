package top.tonydon;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.util.BeanUtils;
import com.fasterxml.jackson.databind.util.BeanUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.context.annotation.RequestScope;
import top.tonydon.constant.BlogRedisConstants;
import top.tonydon.domain.entity.Article;
import top.tonydon.mapper.ArticleMapper;
import top.tonydon.util.MailUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class BlogTest {


    @Resource
    private StringRedisTemplate stringRedisTemplate;

//    @Resource
//    private RedisTemplate<String, Long> stringLongRedisTemplate;

    @Resource
    private RedisTemplate<String, Object> stringObjectRedisTemplate;

    @Resource
    private ArticleMapper articleMapper;


    @Test
    void test() {
        // 获取 viewCount 字符串类型
        String v = (String) stringRedisTemplate.opsForHash()
                .get(BlogRedisConstants.CACHE_ARTICLE_VIEW_COUNT_KEY, "1");
        // 解析为 Long 类型，进行加一
        long viewCount = Long.parseLong(v) + 1;
        // 存入 Redis 中
        stringRedisTemplate.opsForHash()
                .put(BlogRedisConstants.CACHE_ARTICLE_VIEW_COUNT_KEY, "1", Long.toString(viewCount));
    }

    void testRedisTemplate() {
        Long viewCount = (Long) stringObjectRedisTemplate.opsForHash().get(BlogRedisConstants.CACHE_ARTICLE_VIEW_COUNT_KEY, "1");
        stringObjectRedisTemplate.opsForHash().put(BlogRedisConstants.CACHE_ARTICLE_VIEW_COUNT_KEY, "1", viewCount + 1);
    }


    @Test
    void testStringRedisTemplate(){
        String key = "test:incr";
        Long increment = stringRedisTemplate.opsForHash().increment(key, "2", 10);
        System.out.println(increment);
//        String viewCount = (String) stringRedisTemplate.opsForHash().get(key, "1");

//        System.out.println(viewCount);
    }
}
