package top.tonydon.service.impl;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import top.tonydon.constant.SystemConstants;
import top.tonydon.domain.ResponseResult;
import top.tonydon.domain.entity.Article;
import top.tonydon.domain.entity.Category;
import top.tonydon.domain.vo.ArticleDetailVo;
import top.tonydon.domain.vo.ArticleVo;
import top.tonydon.domain.vo.HotArticleVo;
import top.tonydon.domain.vo.PageVo;
import top.tonydon.enums.HttpCodeEnum;
import top.tonydon.mapper.ArticleMapper;
import top.tonydon.mapper.CategoryMapper;
import top.tonydon.service.ArticleService;
import top.tonydon.utils.BeanCopyUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Resource
    private CategoryMapper categoryMapper;

    // 查询热门文章
    @Override
    public ResponseResult<List<HotArticleVo>> hotArticleList() {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        // 必须是正式文章
        wrapper.eq(Article::getIsPublish, SystemConstants.ARTICLE_STATUS_PUBLISH);
        // 安装浏览量进行排序
        wrapper.orderByDesc(Article::getViewCount);
        // 最多查 10 项
        IPage<Article> page = new Page<>(1, 10);
        page(page, wrapper);

        // 查询结果
        List<Article> articleList = page.getRecords();
        // Bean 拷贝，返回数据
        List<HotArticleVo> voList = BeanCopyUtils.copyBeanList(articleList, HotArticleVo.class);
        return ResponseResult.success(voList);
    }

    // 查询文章列表
    @Override
    public ResponseResult<PageVo<ArticleVo>> articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        // 查询条件
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        // 如果有 categoryId，查询时要和传入的相同
        wrapper.eq(categoryId != null && categoryId > 0, Article::getCategoryId, categoryId);
        // 正式发布的文章
        wrapper.eq(Article::getIsPublish, SystemConstants.ARTICLE_STATUS_PUBLISH);
        // 置顶的文章在前，对 isTop 进行降序排序
//        wrapper.orderByDesc(Article::getPublishTime);
        wrapper.orderByDesc((SFunction<Article, LocalDateTime>) Article::getPublishTime);
        // 分页查询
        IPage<Article> iPage = new Page<>(pageNum, pageSize);
        page(iPage, wrapper);
        // 获取文章列名
        List<Article> articleList = iPage.getRecords();
        // 查询 categoryName
        // stream流处理
        articleList = articleList.stream()
                // 获取分类id，查询分类名称
                .map(article -> {
                    Category category = categoryMapper.selectById(article.getCategoryId());
                    return article.setCategoryName(category != null ? category.getName() : "分类不存在");
                }).collect(Collectors.toList());
        // 封装数据
        List<ArticleVo> voList = BeanCopyUtils.copyBeanList(articleList, ArticleVo.class);

        // 封装查询结果，并返回
        return ResponseResult.success(new PageVo<>(voList, iPage.getTotal()));
    }

    // 查询文章详情
    @Override
    public ResponseResult<ArticleDetailVo> getArticleDetail(Long id) {
        // 查询文章
        Article article = getById(id);
        if (article == null){
            return ResponseResult.error(HttpCodeEnum.NO_ID_ERROR);
        }
        // 封装vo
        ArticleDetailVo vo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        // 根据分类id，查询分类名称
        Category category = categoryMapper.selectById(vo.getCategoryId());
        if (category != null) {
            vo.setCategoryName(category.getName());
        }
        // 返回数据
        return ResponseResult.success(vo);
    }


    @Override
    public ResponseResult<Object> addViewCount(Long id) {
        // 访问量加一
        boolean success = lambdaUpdate().setSql("view_count = view_count + 1").eq(Article::getId, id).update();
        if (success) return ResponseResult.success();
        else return ResponseResult.error();
    }
}


