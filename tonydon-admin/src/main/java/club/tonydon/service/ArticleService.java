package club.tonydon.service;

import club.tonydon.domain.ResponseResult;
import club.tonydon.domain.entity.Article;
import club.tonydon.domain.vo.ArticleEditVo;
import club.tonydon.domain.vo.ArticleListVo;
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
     * 更新草稿
     */
    ResponseResult<Object> updateDraft(Article article);

    /**
     * 保存草稿
     */
    ResponseResult<Object> saveDraft(Article article);

    /**
     * 更新文章
     */
    ResponseResult<Object> updateArticle(Article article);

    ResponseResult<List<ArticleListVo>> getArticleList();

    ResponseResult<ArticleEditVo> getArticleDetailById(Long id);
}
