package top.tonydon.task;


import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.tonydon.constant.BlogRedisConstants;
import top.tonydon.domain.entity.Article;
import top.tonydon.service.ArticleService;

import javax.annotation.Resource;
import java.util.Map;

@Component
public class UpdateTask {

    @Resource
    private ArticleService articleService;

    @Resource
    private RedisTemplate<String, Object> stringObjectRedisTemplate;

    // 每天的1点、7点、13点和19点更新访问量缓存到数据库
    @Scheduled(cron = "0 0 1,7,13,19 * * ?")
    public void updateViewCount() {
        System.out.println("---------- ViewCount Redis2MySQL ----------");
        Map<Object, Object> map = stringObjectRedisTemplate.opsForHash()
                .entries(BlogRedisConstants.CACHE_ARTICLE_VIEW_COUNT_KEY);
        map.forEach((k, v) -> articleService.lambdaUpdate()
                .eq(Article::getId, Long.parseLong((String) k))
                .set(Article::getViewCount, v)
                .update());
        System.out.println("---------- ViewCount Redis2MySQL ----------");
    }
}
