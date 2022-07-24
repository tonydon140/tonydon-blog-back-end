package top.tonydon.service.impl;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import top.tonydon.constant.BlogRedisConstants;
import top.tonydon.constant.EntityConstants;
import top.tonydon.domain.ResponseResult;
import top.tonydon.domain.entity.Article;
import top.tonydon.domain.entity.Category;
import top.tonydon.domain.entity.Comment;
import top.tonydon.domain.vo.ArticleDetailVo;
import top.tonydon.domain.vo.ArticleListVo;
import top.tonydon.domain.vo.HotArticleVo;
import top.tonydon.domain.vo.PageVo;
import top.tonydon.enums.HttpCodeEnum;
import top.tonydon.mapper.ArticleMapper;
import top.tonydon.mapper.CommentMapper;
import top.tonydon.service.ArticleService;
import top.tonydon.util.BeanCopyUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.tonydon.util.BlogCache;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Resource
    private BlogCache blogCache;

    @Resource
    private CommentMapper commentMapper;

    // 查询热门文章
    @Override
    public ResponseResult<List<HotArticleVo>> hotArticleList() {
        // 1. 从缓存中查询热门文章
        List<HotArticleVo> voList = blogCache.getWithPenetration(BlogRedisConstants.CACHE_HOT_ARTICLE_KEY, HotArticleVo.class, () -> {
            LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
            // 必须是正式文章
            wrapper.eq(Article::getIsPublish, EntityConstants.ARTICLE_STATUS_PUBLISH);
            // 安装浏览量进行排序
            wrapper.orderByDesc(Article::getViewCount);
            // 最多查 10 项
            IPage<Article> page = new Page<>(1, 10);
            page(page, wrapper);

            // 查询结果
            List<Article> articleList = page.getRecords();

            // Bean 拷贝，返回数据
            return BeanCopyUtils.copyBeanList(articleList, HotArticleVo.class);
        }, BlogRedisConstants.CACHE_HOT_ARTICLE_TTL, TimeUnit.MINUTES);

        // 2. 从缓存中查询访问量
        voList.forEach(hotArticleVo -> hotArticleVo.setViewCount(blogCache.getArticleViewCount(hotArticleVo.getId())));

        //3. 返回结果
        return ResponseResult.success(voList);
    }

    // 查询文章列表
    @Override
    public ResponseResult<PageVo<ArticleListVo>> articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        // 查询条件
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        // 如果有 categoryId，查询时要和传入的相同
        wrapper.eq(categoryId != null && categoryId > 0, Article::getCategoryId, categoryId);
        // 正式发布的文章
        wrapper.eq(Article::getIsPublish, EntityConstants.ARTICLE_STATUS_PUBLISH);
        // 根据发布时间进行降序
        wrapper.orderByDesc((SFunction<Article, LocalDateTime>) Article::getPublishTime);
        // 分页查询
        IPage<Article> iPage = new Page<>(pageNum, pageSize);
        page(iPage, wrapper);
        // 获取文章列名
        List<Article> articleList = iPage.getRecords();
        // 查询 categoryName
        // stream流处理
        articleList = articleList.stream()
                .peek(article -> {
                    // 从缓存中查询分类名称
                    Category category = blogCache.getCategory(article.getCategoryId());
                    article.setCategoryName(category != null ? category.getName() : "分类不存在");
                    // 从缓存查询评论总数
                    article.setCommentCount(blogCache.getArticleCommentCount(article.getId()));
                }).collect(Collectors.toList());
        // 封装数据
        List<ArticleListVo> voList = BeanCopyUtils.copyBeanList(articleList, ArticleListVo.class);

        // 2. 从缓存中查询访问量
        voList.forEach(hotArticleVo -> hotArticleVo.setViewCount(blogCache.getArticleViewCount(hotArticleVo.getId())));

        // 封装查询结果，并返回
        return ResponseResult.success(new PageVo<>(voList, iPage.getTotal()));
    }

    // 查询文章详情
    @Override
    public ResponseResult<ArticleDetailVo> getArticleDetail(Long id) {
        // 1. 从缓存中获取文章
        ArticleDetailVo articleDetailVo = blogCache.getWithPenetration(BlogRedisConstants.CACHE_ARTICLE_KEY, id,
                ArticleDetailVo.class, articleId -> {
                    // 1. 从数据库中查询文章
                    Article article = getById(articleId);

                    // 2. 查询数据不存在，返回 null
                    if (article == null)
                        return null;

                    // 3. 封装返回数据
                    ArticleDetailVo vo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
                    // 3.1 从缓存中查询分类，设置分类名称
                    Category category = blogCache.getCategory(vo.getCategoryId());
                    if (category != null) {
                        vo.setCategoryName(category.getName());
                    }
                    // 3.2 从数据库中查询评论总数
                    LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
                    queryWrapper.eq(Comment::getArticleId, article.getId());
                    vo.setCommentCount(commentMapper.selectCount(queryWrapper));
                    return vo;
                }, BlogRedisConstants.CACHE_ARTICLE_TTL, TimeUnit.MINUTES);

        if (articleDetailVo == null)
            return ResponseResult.error(HttpCodeEnum.ARTICLE_NOT_EXIST);

        // 2. 从缓存中查询访问量并加一
        articleDetailVo.setViewCount(blogCache.incrementViewCount(articleDetailVo.getId()));

        // 3. 返回数据
        return ResponseResult.success(articleDetailVo);
    }
}


