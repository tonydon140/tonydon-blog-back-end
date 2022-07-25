package top.tonydon.controller;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.tonydon.constant.BlogRedisConstants;
import top.tonydon.domain.entity.Article;
import top.tonydon.service.ArticleService;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Resource
    private ArticleService articleService;

    @Resource
    private RedisTemplate<String, Object> stringObjectRedisTemplate;

    @RequestMapping("/redis/view_count2db")
    public void viewCount2DB() {
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
