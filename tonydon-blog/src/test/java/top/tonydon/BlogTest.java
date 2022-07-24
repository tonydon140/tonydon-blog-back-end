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
    private StringRedisTemplate template;

//    @Resource
//    private RedisTemplate<String, Long> stringLongRedisTemplate;

    @Resource
    private RedisTemplate<String, Object> stringObjectRedisTemplate;

    @Resource
    private ArticleMapper articleMapper;


    @Test
    void test(){
        System.out.println(stringObjectRedisTemplate.opsForHash().get(BlogRedisConstants.CACHE_ARTICLE_VIEW_COUNT_KEY, "1"));
    }

}
