package club.tonydon.service.impl;

import club.tonydon.domain.entity.Category;
import club.tonydon.domain.entity.User;
import club.tonydon.domain.vo.ArticleEditVo;
import club.tonydon.domain.vo.ArticleListVo;
import club.tonydon.mapper.UserMapper;
import club.tonydon.service.ArticleService;
import club.tonydon.constant.SystemConstants;
import club.tonydon.domain.ResponseResult;
import club.tonydon.domain.entity.Article;
import club.tonydon.exception.NoIdException;
import club.tonydon.mapper.ArticleMapper;
import club.tonydon.mapper.CategoryMapper;
import club.tonydon.utils.BeanCopyUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private UserMapper userMapper;

    // todo 设置文章摘要

    @Override
    public ResponseResult<Object> publishNew(Article article) {
        // 设置更新时间、更新人、发布时间、状态
        article.setUpdateTime(new Date());
        article.setPublishTime(new Date());
        article.setUpdateBy(article.getPublishBy());
        article.setIsPublish(SystemConstants.ARTICLE_STATUS_PUBLISH);

        // 设置摘要
        String content = article.getContent();
        if (content.length() > 160)
            article.setSummary(content.substring(0, 160));
        else
            article.setSummary(content);

        // 保存文章
        save(article);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult<Object> publishDraft(Article article) {
        // 设置更新时间、更新人、发布时间、状态
        article.setUpdateTime(new Date());
        article.setPublishTime(new Date());
        article.setUpdateBy(article.getPublishBy());
        article.setIsPublish(SystemConstants.ARTICLE_STATUS_PUBLISH);

        // 设置摘要
        String content = article.getContent();
        if (content.length() > 160)
            article.setSummary(content.substring(0, 160));
        else
            article.setSummary(content);

        // 更新文章
        updateById(article);
        return ResponseResult.success();
    }


    @Override
    public ResponseResult<Object> updateDraft(Article article) {
        // 设置更新时间
        article.setUpdateTime(new Date());
        updateById(article);
        return ResponseResult.success();
    }

    /**
     * 保存新的草稿
     *
     * @param article 草稿文章
     * @return 文章信息
     */
    @Override
    public ResponseResult<Object> saveDraft(Article article) {
        // 设置更新时间
        article.setUpdateTime(new Date());
        // 保存文章草稿
        save(article);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult<Object> updateArticle(Article article) {
        // 设置更新时间
        article.setUpdateTime(new Date());

        // 更新摘要
        String content = article.getContent();
        if (content.length() > 160)
            article.setSummary(content.substring(0, 160));
        else
            article.setSummary(content);

        // 更新文章
        updateById(article);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult<List<ArticleListVo>> getArticleList() {
        // 1. 查询所有的文章
        List<Article> articleList = list();

        // 2. 对文章进行处理
        articleList = articleList.stream().peek(this::paddingData).collect(Collectors.toList());

        // 3. 封装为 vo 对象并返回
        List<ArticleListVo> voList = BeanCopyUtils.copyBeanList(articleList, ArticleListVo.class);
        return ResponseResult.success(voList);
    }

    @Override
    public ResponseResult<ArticleEditVo> getArticleDetailById(Long id) {
        Article article = getById(id);
        if (article == null) throw new NoIdException();

        // 填充数据
        paddingData(article);

        // 封装 vo 对象并返回
        ArticleEditVo vo = BeanCopyUtils.copyBean(article, ArticleEditVo.class);
        return ResponseResult.success(vo);
    }

    /**
     * 填充数据
     *
     * @param article 文章
     */
    private void paddingData(Article article) {
        // 设置分类名称
        Category category = categoryMapper.selectById(article.getCategoryId());
        article.setCategoryName(category != null ? category.getName() : "分类不存在");
        // 设置创建人昵称
        if (article.getPublishBy() != null) {
            User user = userMapper.selectById(article.getPublishBy());
            article.setPublishName(user != null ? user.getNickname() : "用户不存在");
        }
        // 设置更新人昵称
        if (article.getUpdateBy() != null) {
            User user = userMapper.selectById(article.getUpdateBy());
            article.setUpdateName(user != null ? user.getNickname() : "用户不存在");
        }
    }

}
