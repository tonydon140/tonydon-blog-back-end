package club.tonydon.service;

import club.tonydon.domain.ResponseResult;
import club.tonydon.domain.entity.Article;
import club.tonydon.domain.vo.ArticleDetailVo;
import club.tonydon.domain.vo.ArticleVo;
import club.tonydon.domain.vo.HotArticleVo;
import club.tonydon.domain.vo.PageVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface ArticleService extends IService<Article> {
    /**
     * 获取热门文章列表
     * @return 热门文章列表
     */
    ResponseResult<List<HotArticleVo>> hotArticleList();

    // 查询文章列表
    ResponseResult<PageVo<ArticleVo>> articleList(Integer pageNum, Integer pageSize, Long categoryId);

    // 获取文章详情
    ResponseResult<ArticleDetailVo>  getArticleDetail(Long id);


    /**
     * 添加访问量
     * @param id 文章id
     * @return 响应数据
     */
    ResponseResult<Object> addViewCount(Long id);
}
