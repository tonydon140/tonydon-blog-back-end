package top.tonydon.util;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import top.tonydon.constant.BlogRedisConstants;
import top.tonydon.domain.entity.Article;
import top.tonydon.domain.entity.Category;
import top.tonydon.domain.entity.Comment;
import top.tonydon.domain.vo.ArticleVo;
import top.tonydon.mapper.ArticleMapper;
import top.tonydon.mapper.CategoryMapper;
import top.tonydon.mapper.CommentMapper;

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

    public BlogCache(StringRedisTemplate template) {
        super(template);
    }


    /**
     * 根据id从缓存中获取 Category
     *
     * @param id 分类 id
     * @return Category
     */
    public Category getCategory(Long id) {
        return getWithPenetration(BlogRedisConstants.CACHE_CATEGORY_KEY, id, Category.class, categoryMapper::selectById,
                BlogRedisConstants.CACHE_CATEGORY_TTL, TimeUnit.MINUTES);
    }


    /**
     * 根据 id 从缓存中获取 ArticleVo
     *
     * @param id 文章id
     * @return ArticleVo
     */
    public ArticleVo getArticleVo(Long id) {
        return getWithPenetration(BlogRedisConstants.CACHE_ARTICLE_KEY, id,
                ArticleVo.class, articleId -> {
                    // 1. 从数据库中查询文章
                    Article article = articleMapper.selectById(articleId);

                    // 2. 查询数据不存在，返回 null
                    if (article == null)
                        return null;

                    // 3. 封装返回数据
                    ArticleVo vo = BeanCopyUtils.copyBean(article, ArticleVo.class);
                    // 3.1 从缓存中查询分类，设置分类名称
                    Category category = getCategory(vo.getCategoryId());
                    vo.setCategoryName(category != null ? category.getName() : "分类不存在");
                    // 3.2 从数据库中查询评论总数
                    vo.setCommentCount(getArticleCommentCount(vo.getId()));
                    return vo;
                }, BlogRedisConstants.CACHE_ARTICLE_TTL, TimeUnit.MINUTES);
    }

    /**
     * 根据 id 从缓存中获取 Comment
     *
     * @param id 评论 id
     * @return Comment
     */
    public Comment getComment(Long id) {
        return getWithPenetration(BlogRedisConstants.CACHE_COMMENT_KEY, id, Comment.class, commentMapper::selectById,
                BlogRedisConstants.CACHE_COMMENT_TTL, TimeUnit.MINUTES);
    }


    /**
     * 根据文章 id 从缓存中查询该文章所拥有的评论数量
     *
     * @param id 文章 id
     * @return 评论数量
     */
    public Long getArticleCommentCount(Long id) {
        return getWithPenetration(BlogRedisConstants.CACHE_ARTICLE_COMMENT_COUNT_KEY, id, Long.class, aid -> {
            // 从数据库查询评论总数
            LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Comment::getArticleId, id);
            return commentMapper.selectCount(queryWrapper);
        }, BlogRedisConstants.CACHE_ARTICLE_COMMENT_COUNT_TTL, TimeUnit.MINUTES);
    }

    // 从缓存中查询文章访问量
    public Long getArticleViewCount(Long id, Long viewCountInDB) {
        String idStr = id.toString();
        // 1. 从缓存中查询访问量
        String viewCount = (String) stringRedisTemplate.opsForHash()
                .get(BlogRedisConstants.CACHE_ARTICLE_VIEW_COUNT_KEY, idStr);

        // 2. 命中缓存，直接返回
        if (viewCount != null) {
            return Long.parseLong(viewCount);
        }

        // 3. 未命中缓存，根据数据库中的访问量 viewCountInDB 建立缓存并返回
        stringRedisTemplate.opsForHash()
                .increment(BlogRedisConstants.CACHE_ARTICLE_VIEW_COUNT_KEY, idStr, viewCountInDB);
        return viewCountInDB;
    }

    public Long incrementViewCount(Long id, Long viewCountInDB) {
        String idStr = id.toString();
        // 1. 判断缓存是否存在
        Boolean hasKey = stringRedisTemplate.opsForHash()
                .hasKey(BlogRedisConstants.CACHE_ARTICLE_VIEW_COUNT_KEY, idStr);

        if (hasKey) {
            // 缓存存在，自增一并返回
            return stringRedisTemplate.opsForHash()
                    .increment(BlogRedisConstants.CACHE_ARTICLE_VIEW_COUNT_KEY, idStr, 1);
        } else {
            // 缓存不存在，自增 viewCountInDB + 1 建立缓存
            return stringRedisTemplate.opsForHash()
                    .increment(BlogRedisConstants.CACHE_ARTICLE_VIEW_COUNT_KEY, idStr, viewCountInDB + 1);
        }
    }


}
