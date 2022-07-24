package top.tonydon.service;

import top.tonydon.domain.ResponseResult;
import top.tonydon.domain.entity.Article;
import top.tonydon.domain.vo.ArticleDetailVo;
import top.tonydon.domain.vo.ArticleListVo;
import top.tonydon.domain.vo.HotArticleVo;
import top.tonydon.domain.vo.PageVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface ArticleService extends IService<Article> {
    /**
     * 获取热门文章列表
     * @return 热门文章列表
     */
    ResponseResult<List<HotArticleVo>> hotArticleList();

    // 查询文章列表
    ResponseResult<PageVo<ArticleListVo>> articleList(Integer pageNum, Integer pageSize, Long categoryId);

    // 获取文章详情
    ResponseResult<ArticleDetailVo>  getArticleDetail(Long id);

}
