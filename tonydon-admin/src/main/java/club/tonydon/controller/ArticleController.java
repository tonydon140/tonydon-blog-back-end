package club.tonydon.controller;

import club.tonydon.domain.ResponseResult;
import club.tonydon.domain.entity.Article;
import club.tonydon.domain.vo.ArticleEditVo;
import club.tonydon.domain.vo.ArticleListVo;
import club.tonydon.service.ArticleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Resource
    private ArticleService articleService;


    /**
     * 发布文章
     * 如果 id 为空，则发布新文章
     * 如果 id 不为空，则更新草稿文章并发布
     */
    @PostMapping("/publish")
    public ResponseResult<Object> publishArticle(@RequestBody Article article) {
        if (article.getId() == null) return articleService.publishNew(article);
        else return articleService.publishDraft(article);
    }


    /**
     * 保存草稿文章
     * 如果 id 为空，则保存新文章
     * 如果 id 不为空，则更新文章
     */
    @PostMapping("/draft")
    public ResponseResult<Object> saveDraftArticle(@RequestBody Article article) {
        if (article.getId() == null) return articleService.saveDraft(article);
        else return articleService.updateDraft(article);
    }

    @GetMapping
    public ResponseResult<List<ArticleListVo>> getArticleList() {
        return articleService.getArticleList();
    }

    @GetMapping("/{id}")
    public ResponseResult<ArticleEditVo> getArticleDetail(@PathVariable Long id) {
        return articleService.getArticleDetailById(id);
    }

}
