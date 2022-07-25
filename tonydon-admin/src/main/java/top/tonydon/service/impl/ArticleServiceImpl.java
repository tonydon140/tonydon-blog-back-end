package top.tonydon.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.tonydon.constant.AdminRedisConstants;
import top.tonydon.constant.BlogRedisConstants;
import top.tonydon.domain.entity.Category;
import top.tonydon.domain.entity.User;
import top.tonydon.domain.vo.ArticleEditVo;
import top.tonydon.domain.vo.ArticleListVo;
import top.tonydon.domain.vo.PageVo;
import top.tonydon.service.ArticleService;
import top.tonydon.constant.EntityConstants;
import top.tonydon.domain.ResponseResult;
import top.tonydon.domain.entity.Article;
import top.tonydon.exception.NoIdException;
import top.tonydon.mapper.ArticleMapper;
import top.tonydon.util.AdminCache;
import top.tonydon.util.BeanCopyUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Resource
    private AdminCache adminCache;

    @Override
    public ResponseResult<Object> publishNew(Article article) {
        // 设置更新时间、更新人、发布时间、状态
        article.setUpdateBy(article.getPublishBy());
        article.setIsPublish(EntityConstants.ARTICLE_STATUS_PUBLISH);

        // 设置摘要
        String content = article.getContent();
        if (content.length() > 160)
            article.setSummary(content.substring(0, 160));
        else
            article.setSummary(content);

        // 保存文章
        save(article);
        // 删除分类缓存
        adminCache.delete(BlogRedisConstants.CACHE_CATEGORY_LIST_KEY);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult<Object> publishDraft(Article article) {
        // 设置更新时间、更新人、发布时间、状态
        article.setUpdateBy(article.getPublishBy());
        article.setIsPublish(EntityConstants.ARTICLE_STATUS_PUBLISH);

        // 设置摘要
        String content = article.getContent();
        if (content.length() > 160)
            article.setSummary(content.substring(0, 160));
        else
            article.setSummary(content);

        // 更新文章
        updateById(article);
        // 删除分类缓存
        adminCache.delete(BlogRedisConstants.CACHE_CATEGORY_LIST_KEY);
        return ResponseResult.success();
    }


    @Override
    public ResponseResult<Object> updateArticle(Article article) {
        // 1. 更新摘要
        String content = article.getContent();
        if (content.length() > 160)
            article.setSummary(content.substring(0, 160));
        else
            article.setSummary(content);

        // 2. 更新文章
        updateById(article);

        // 3. 删除缓存
        adminCache.delete(AdminRedisConstants.CACHE_ARTICLE_KEY + article.getId());
        adminCache.delete(BlogRedisConstants.CACHE_ARTICLE_KEY + article.getId());

        return ResponseResult.success();
    }

    @Override
    public ResponseResult<Object> remove(Long id) {
        // 1. 删除文章
        removeById(id);

        // 2. 删除缓存
        adminCache.delete(AdminRedisConstants.CACHE_ARTICLE_KEY + id);
        adminCache.delete(BlogRedisConstants.CACHE_ARTICLE_KEY + id);

        // 3. 返回
        return ResponseResult.success();
    }

    @Override
    public ResponseResult<PageVo<ArticleListVo>> findPage(Integer pageNum, Integer pageSize) {
        // 1. 分页查询
        IPage<Article> articleIPage = new Page<>(pageNum, pageSize);
        List<Article> articleList = page(articleIPage).getRecords();
        List<ArticleListVo> voList = BeanCopyUtils.copyBeanList(articleList, ArticleListVo.class);

        // 2. 填充数据
        voList = voList.stream().peek(this::paddingData).collect(Collectors.toList());

        // 3. 返回数据
        return ResponseResult.success(new PageVo<>(voList, articleIPage.getTotal()));
    }

    @Override
    public ResponseResult<ArticleEditVo> findById(Long id) {
        // 1. 从缓存中获取文章
        Article article = adminCache.getArticle(id);
        if (article == null)
            throw new NoIdException();
        ArticleEditVo vo = BeanCopyUtils.copyBean(article, ArticleEditVo.class);

        // 2. 填充数据
        // 设置创建人昵称
        if (vo.getPublishBy() != null) {
            User user = adminCache.getUser(vo.getPublishBy());
            vo.setPublishName(user != null ? user.getNickname() : "用户不存在");
        }
        // 设置更新人昵称
        if (vo.getUpdateBy() != null) {
            User user = adminCache.getUser(vo.getUpdateBy());
            vo.setUpdateName(user != null ? user.getNickname() : "用户不存在");
        }

        // 3. 返回
        return ResponseResult.success(vo);
    }

    /**
     * 填充数据
     *
     * @param vo 文章
     */
    private void paddingData(ArticleListVo vo) {
        // 设置分类名称
        Category category = adminCache.getCategory(vo.getCategoryId());
        vo.setCategoryName(category != null ? category.getName() : "分类不存在");

        // 设置创建人昵称
        if (vo.getPublishBy() != null) {
            User user = adminCache.getUser(vo.getPublishBy());
            vo.setPublishName(user != null ? user.getNickname() : "用户不存在");
        }

        // 设置更新人昵称
        if (vo.getUpdateBy() != null) {
            User user = adminCache.getUser(vo.getUpdateBy());
            vo.setUpdateName(user != null ? user.getNickname() : "用户不存在");
        }
    }


}
