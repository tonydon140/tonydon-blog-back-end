package top.tonydon.controller;

import top.tonydon.domain.ResponseResult;
import top.tonydon.domain.vo.*;
import top.tonydon.service.ArticleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    // 获取热门文章
    @GetMapping("/hot")
    public ResponseResult<List<HotArticleVo>> hotArticleList() {
        return articleService.hotArticleList();
    }


    // 获取文章列表
    @GetMapping("/{pageNum}/{pageSize}/{categoryId}")
    public ResponseResult<PageVo<ArticleListVo>> articleList(@PathVariable Integer pageNum,
                                                             @PathVariable Integer pageSize,
                                                             @PathVariable Long categoryId) {
        return articleService.articleList(pageNum, pageSize, categoryId);
    }


    // 根据文章id获取文章内容
    @GetMapping("/{id}")
    public ResponseResult<ArticleVo> getArticleDetail(@PathVariable Long id) {
        return articleService.getArticleDetail(id);
    }


}
