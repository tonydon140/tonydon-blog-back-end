package top.tonydon.service;

import top.tonydon.domain.ResponseResult;
import top.tonydon.domain.entity.Article;
import top.tonydon.domain.vo.ArticleEditVo;
import top.tonydon.domain.vo.ArticleListVo;
import com.baomidou.mybatisplus.extension.service.IService;
import top.tonydon.domain.vo.PageVo;

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

    ResponseResult<Object> remove(Long id);

    ResponseResult<PageVo<ArticleListVo>> findPage(Integer pageNum, Integer pageSize);

    ResponseResult<ArticleEditVo> findById(Long id);
}
