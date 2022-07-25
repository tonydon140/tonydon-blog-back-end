package top.tonydon.util;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import top.tonydon.constant.AdminRedisConstants;
import top.tonydon.domain.entity.Article;
import top.tonydon.domain.entity.Category;
import top.tonydon.domain.entity.Comment;
import top.tonydon.domain.entity.User;
import top.tonydon.mapper.ArticleMapper;
import top.tonydon.mapper.CategoryMapper;
import top.tonydon.mapper.CommentMapper;
import top.tonydon.mapper.UserMapper;
import top.tonydon.util.cache.CacheClient;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
public class AdminCache extends CacheClient {

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private CommentMapper commentMapper;

    public AdminCache(StringRedisTemplate template) {
        super(template);
    }

    /**
     * 根据分类 id 从缓存中获取 Category
     *
     * @param id 分类 id
     * @return Category
     */
    public Category getCategory(Long id) {
        return getWithPenetration(AdminRedisConstants.CACHE_CATEGORY_KEY, id, Category.class, categoryMapper::selectById,
                AdminRedisConstants.CACHE_CATEGORY_TTL, TimeUnit.MINUTES);
    }


    /**
     * 根据用户 id 从缓存中获取 User
     *
     * @param id 用户 id
     * @return User
     */
    public User getUser(Long id){
        return getWithPenetration(AdminRedisConstants.CACHE_USER_KEY, id, User.class, userMapper::selectById,
                AdminRedisConstants.CACHE_USER_TTL, TimeUnit.MINUTES);
    }

    /**
     * 根据文章 id 从缓存中获取 Article
     *
     * @param id 文章 id
     * @return Article
     */
    public Article getArticle(Long id){
        return getWithPenetration(AdminRedisConstants.CACHE_ARTICLE_KEY, id, Article.class, articleMapper::selectById,
                AdminRedisConstants.CACHE_ARTICLE_TTL, TimeUnit.MINUTES);
    }

    /**
     * 根据评论 id 从缓存中获取 Comment
     *
     * @param id 评论 id
     * @return Comment
     */
    public Comment getComment(Long id){
        return getWithPenetration(AdminRedisConstants.CACHE_COMMENT_KEY, id, Comment.class, commentMapper::selectById,
                AdminRedisConstants.CACHE_COMMENT_TTL, TimeUnit.MINUTES);
    }

}
