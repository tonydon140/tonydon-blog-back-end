package club.tonydon.service.impl;

import club.tonydon.contant.SysContants;
import club.tonydon.domain.ResponseResult;
import club.tonydon.domain.entity.Article;
import club.tonydon.domain.entity.Category;
import club.tonydon.domain.vo.CategoryVo;
import club.tonydon.mapper.CategoryMapper;
import club.tonydon.service.ArticleService;
import club.tonydon.service.CategoryService;
import club.tonydon.utils.BeanCopyUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
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
    private ArticleService articleService;

    /**
     * 获取分类列表，用于前台展示
     * @return 分类列表
     */
    @Override
    public ResponseResult<List<CategoryVo>> getAll() {
        // 查询文章表，状态为已发布的文章
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getStatus, SysContants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(wrapper);

        // 获取文章的分类id，并且去重
        Set<Long> categoryIds = articleList.stream()
                .map(Article::getCategoryId)
                .collect(Collectors.toSet());

        // 查询分类表，必须是正常的分类，且分类必须有文章存在
        LambdaQueryWrapper<Category> categoryWrapper = new LambdaQueryWrapper<>();
        categoryWrapper
                .eq(Category::getStatus, SysContants.CATEGORY_STATUS_NORMAL)
                .in(Category::getId, categoryIds);
        List<Category> categoryList = list(categoryWrapper);

        // 封装vo
        List<CategoryVo> voList = BeanCopyUtils.copyBeanList(categoryList, CategoryVo.class);
        return ResponseResult.success(voList);
    }

}

