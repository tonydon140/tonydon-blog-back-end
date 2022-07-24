package top.tonydon.util;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import top.tonydon.constant.BlogRedisConstants;
import top.tonydon.domain.entity.Article;
import top.tonydon.domain.entity.Category;
import top.tonydon.domain.entity.Comment;
import top.tonydon.mapper.ArticleMapper;
import top.tonydon.mapper.CategoryMapper;
import top.tonydon.mapper.CommentMapper;
import top.tonydon.util.cache.CacheClient;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
public class BlogCache extends CacheClient {

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private RedisTemplate<String, Object> stringObjectRedisTemplate;

    public BlogCache(StringRedisTemplate template) {
        super(template);
    }

    // 从缓存中根据id获取分类
    public Category getCategory(Long id) {
        return getWithPenetration(BlogRedisConstants.CACHE_CATEGORY_KEY, id, Category.class, categoryMapper::selectById,
                BlogRedisConstants.CACHE_CATEGORY_TTL, TimeUnit.MINUTES);
    }

    // 从缓存中查询文章的评论数量
    public Long getArticleCommentCount(Long id) {
        return getWithPenetration(BlogRedisConstants.CACHE_ARTICLE_COMMENT_COUNT_KEY, id, Long.class, aid -> {
            // 从数据库查询评论总数
            LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Comment::getArticleId, id);
            return commentMapper.selectCount(queryWrapper);
        }, BlogRedisConstants.CACHE_ARTICLE_COMMENT_COUNT_TTL, TimeUnit.MINUTES);
    }

    // 从缓存中查询文章访问量
    public Long getArticleViewCount(Long id) {
        // 1. 从缓存中查询访问量
        Long viewCount = (Long) stringObjectRedisTemplate.opsForHash()
                .get(BlogRedisConstants.CACHE_ARTICLE_VIEW_COUNT_KEY, id.toString());

        // 2. 命中缓存，直接返回
        if (viewCount != null) {
            return viewCount;
        }

        // 3. 未命中缓存，查询数据库
        Article article = articleMapper.selectById(id);

        // 3.1 数据不存在，返回 null
        if (article == null) {
            return null;
        }

        // 3.2 数据存在，建立缓存并返回
        stringObjectRedisTemplate.opsForHash()
                .put(BlogRedisConstants.CACHE_ARTICLE_VIEW_COUNT_KEY, id.toString(), article.getViewCount());
        return article.getViewCount();
    }

    public Long incrementViewCount(Long id) {
        // 1. 查询访问量
        Long viewCount = getArticleViewCount(id);
        if (viewCount == null) return null;

        // 2. 访问量加一
        stringObjectRedisTemplate.opsForHash()
                .put(BlogRedisConstants.CACHE_ARTICLE_VIEW_COUNT_KEY, id.toString(), viewCount + 1);

        // 3. 返回加一后的访问量
        return viewCount + 1;
    }
}
