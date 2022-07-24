package top.tonydon.service.impl;

import top.tonydon.constant.BlogRedisConstants;
import top.tonydon.constant.EntityConstants;
import top.tonydon.domain.ResponseResult;
import top.tonydon.domain.entity.Article;
import top.tonydon.domain.entity.Category;
import top.tonydon.domain.vo.CategoryVo;
import top.tonydon.mapper.ArticleMapper;
import top.tonydon.mapper.CategoryMapper;
import top.tonydon.service.CategoryService;
import top.tonydon.util.BeanCopyUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.tonydon.util.BlogCache;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2022-03-18 17:25:48
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private BlogCache blogCache;

    /**
     * 获取分类列表，用于前台展示
     *
     * @return 分类列表
     */
    @Override
    public ResponseResult<List<CategoryVo>> findAll() {
        // 从缓存中查询
        List<CategoryVo> voList = blogCache.getWithPenetration(BlogRedisConstants.CACHE_CATEGORY_LIST_KEY, CategoryVo.class,
                () -> {
            // 查询文章表，状态为已发布的文章
            LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Article::getIsPublish, EntityConstants.ARTICLE_STATUS_PUBLISH);
            List<Article> articleList = articleMapper.selectList(wrapper);
            if (articleList.size() == 0)
                return null;

            // 获取文章的分类id，并且去重
            Set<Long> categoryIds = articleList.stream()
                    .map(Article::getCategoryId)
                    .collect(Collectors.toSet());

            // 查询分类表，必须是正常的分类，且分类必须有文章存在
            LambdaQueryWrapper<Category> categoryWrapper = new LambdaQueryWrapper<>();
            categoryWrapper
                    .eq(Category::getStatus, EntityConstants.CATEGORY_STATUS_NORMAL)
                    .in(Category::getId, categoryIds);
            List<Category> categoryList = list(categoryWrapper);

            // 封装vo
            return BeanCopyUtils.copyBeanList(categoryList, CategoryVo.class);
        }, BlogRedisConstants.CACHE_CATEGORY_LIST_TTL, TimeUnit.MINUTES);


        return ResponseResult.success(voList);
    }

}

