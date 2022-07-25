package top.tonydon.service.impl;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import top.tonydon.constant.BlogRedisConstants;
import top.tonydon.constant.EntityConstants;
import top.tonydon.domain.ResponseResult;
import top.tonydon.domain.entity.Article;
import top.tonydon.domain.entity.Category;
import top.tonydon.domain.vo.*;
import top.tonydon.enums.HttpCodeEnum;
import top.tonydon.mapper.ArticleMapper;
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
        voList.forEach(hot -> hot.setViewCount(blogCache.getArticleViewCount(hot.getId(), hot.getViewCount())));

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
        List<Article> articleList = iPage.getRecords();
        List<ArticleListVo> voList = BeanCopyUtils.copyBeanList(articleList, ArticleListVo.class);

        // 填充数据：分类名称、评论总数、访问量
        voList = voList.stream()
                .peek(vo -> {
                    // 从缓存中查询分类名称
                    Category category = blogCache.getCategory(vo.getCategoryId());
                    vo.setCategoryName(category != null ? category.getName() : "分类不存在");
                    // 从缓存查询评论总数
                    vo.setCommentCount(blogCache.getArticleCommentCount(vo.getId()));
                    // 从缓存中查询访问量
                    vo.setViewCount(blogCache.getArticleViewCount(vo.getId(), vo.getViewCount()));
                }).collect(Collectors.toList());

        // 封装查询结果，并返回
        return ResponseResult.success(new PageVo<>(voList, iPage.getTotal()));
    }

    // 查询文章详情
    @Override
    public ResponseResult<ArticleVo> getArticleDetail(Long id) {
        // 1. 从缓存中获取文章
        ArticleVo articleVo = blogCache.getArticleVo(id);

        if (articleVo == null)
            return ResponseResult.error(HttpCodeEnum.ARTICLE_NOT_EXIST);

        // 2. 从缓存中查询访问量并加一
        articleVo.setViewCount(blogCache.incrementViewCount(articleVo.getId(), articleVo.getViewCount()));

        // 3. 返回数据
        return ResponseResult.success(articleVo);
    }
}


