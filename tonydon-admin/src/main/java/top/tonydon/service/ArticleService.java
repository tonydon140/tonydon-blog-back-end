package top.tonydon.service;

import top.tonydon.domain.ResponseResult;
import top.tonydon.domain.entity.Article;
import top.tonydon.domain.vo.ArticleEditVo;
import top.tonydon.domain.vo.ArticleListVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface ArticleService extends IService<Article> {
    /**
     * 发布新文章
     */
    ResponseResult<Object> publishNew(Article article);

    /**
     * 发布草稿
     */
    ResponseResult<Object> publishDraft(Article article);

    /**
     * 更新已发布的文章
     */
    ResponseResult<Object> updateArticle(Article article);

    ResponseResult<List<ArticleListVo>> getArticleList();

    ResponseResult<ArticleEditVo> getArticleDetailById(Long id);
}
