package club.tonydon.service;

import club.tonydon.domain.ResponseResult;
import club.tonydon.domain.entity.Article;
import club.tonydon.domain.vo.ArticleListVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface ArticleService extends IService<Article> {
    ResponseResult<Object> saveArticle(Article article);

    ResponseResult<Object> publish(Article article);

    ResponseResult<Object> updateDraft(Article article);

    ResponseResult<Object> saveNewDraft(Article article);


    ResponseResult<List<ArticleListVo>> getArticleList();
}
