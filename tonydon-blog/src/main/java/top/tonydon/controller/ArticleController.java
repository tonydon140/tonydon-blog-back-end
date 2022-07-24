package top.tonydon.controller;

import top.tonydon.domain.ResponseResult;
import top.tonydon.domain.vo.ArticleDetailVo;
import top.tonydon.domain.vo.ArticleListVo;
import top.tonydon.domain.vo.HotArticleVo;
import top.tonydon.domain.vo.PageVo;
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
    public ResponseResult<ArticleDetailVo> getArticleDetail(@PathVariable Long id) {
        return articleService.getArticleDetail(id);
    }

//    /**
//     * 添加访问量
//     *
//     * @param id 文章 id
//     * @return 响应数据
//     */
//    @PutMapping("/updateViewCount/{id}")
//    public ResponseResult<Object> updateViewCount(@PathVariable Long id) {
//        return articleService.addViewCount(id);
//    }

}
