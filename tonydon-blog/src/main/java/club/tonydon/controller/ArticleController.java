package club.tonydon.controller;

import club.tonydon.domain.ResponseResult;
import club.tonydon.domain.vo.ArticleDetailVo;
import club.tonydon.domain.vo.ArticleVo;
import club.tonydon.domain.vo.HotArticleVo;
import club.tonydon.domain.vo.PageVo;
import club.tonydon.service.ArticleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    // 获取热门文章
    @GetMapping("/hotArticleList")
    public ResponseResult<List<HotArticleVo>> hotArticleList() {
        return articleService.hotArticleList();
    }

    // 获取文章列表
    @GetMapping("/articleList")
    public ResponseResult<PageVo<ArticleVo>> articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        return articleService.articleList(pageNum, pageSize, categoryId);
    }

    // 根据文章id获取文章内容
    @GetMapping("/{id}")
    public ResponseResult<ArticleDetailVo> getArticleDetail(@PathVariable Long id) {
        return articleService.getArticleDetail(id);
    }

    /**
     * 添加访问量
     *
     * @param id 文章 id
     * @return 响应数据
     */
    @PutMapping("/updateViewCount/{id}")
    public ResponseResult<Object> updateViewCount(@PathVariable Long id) {
        return articleService.addViewCount(id);
    }

}
