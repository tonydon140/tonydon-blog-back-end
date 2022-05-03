package club.tonydon.service.impl;

import club.tonydon.contant.SysConsts;
import club.tonydon.domain.ResponseResult;
import club.tonydon.domain.entity.Article;
import club.tonydon.domain.vo.ArticleEditVo;
import club.tonydon.domain.vo.ArticleListVo;
import club.tonydon.exception.NoIdException;
import club.tonydon.mapper.ArticleMapper;
import club.tonydon.mapper.CategoryMapper;
import club.tonydon.mapper.UserMapper;
import club.tonydon.service.ArticleService;
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


    @Override
    public ResponseResult<Object> publishNew(Article article) {
        // 设置更新时间、更新人、发布时间、状态
        article.setUpdateTime(new Date());
        article.setPublishTime(new Date());
        article.setUpdateBy(article.getPublishBy());
        article.setIsPublish(SysConsts.ARTICLE_STATUS_PUBLISH);
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
        article.setIsPublish(SysConsts.ARTICLE_STATUS_PUBLISH);
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
        // 更新文章
        updateById(article);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult<List<ArticleListVo>> getArticleList() {
        // 1. 查询所有的文章
        List<Article> articleList = list();

        // 2. 对文章进行处理
        articleList = articleList.stream().peek(article -> {
            article.setCategoryName(categoryMapper.selectById(article.getCategoryId()).getName());
            if (article.getPublishBy() != null)
                article.setPublishName(userMapper.selectById(article.getPublishBy()).getUsername());
            if (article.getUpdateBy() != null)
                article.setUpdateName(userMapper.selectById(article.getUpdateBy()).getUsername());
        }).collect(Collectors.toList());


        // 3. 封装为 vo 对象并返回
        List<ArticleListVo> voList = BeanCopyUtils.copyBeanList(articleList, ArticleListVo.class);
        return ResponseResult.success(voList);
    }

    @Override
    public ResponseResult<ArticleEditVo> getArticleDetailById(Long id) {
        Article article = getById(id);
        if (article == null) throw new NoIdException();

        // 填充数据
        article.setCategoryName(categoryMapper.selectById(article.getCategoryId()).getName());
        if (article.getPublishBy() != null)
            article.setPublishName(userMapper.selectById(article.getPublishBy()).getUsername());
        if (article.getUpdateBy() != null)
            article.setUpdateName(userMapper.selectById(article.getUpdateBy()).getUsername());

        // 封装 vo 对象并返回
        ArticleEditVo vo = BeanCopyUtils.copyBean(article, ArticleEditVo.class);
        return ResponseResult.success(vo);
    }

}
