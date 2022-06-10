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

    /**
     * 获取文章列表
     *
     * @return 文章集合
     */
    @GetMapping
    public ResponseResult<List<ArticleListVo>> getArticleList() {
        return articleService.getArticleList();
    }

    /**
     * 根据 id 返回文章编辑视图
     *
     * @param id id
     * @return 文章编辑视图
     */
    @GetMapping("/{id}")
    public ResponseResult<ArticleEditVo> getArticleDetail(@PathVariable Long id) {
        return articleService.getArticleDetailById(id);
    }

    /**
     * 更新已发布的文章
     */
    @PutMapping("/{id}")
    public ResponseResult<Object> updateArticle(@RequestBody Article article) {
        return articleService.updateArticle(article);
    }

    /**
     * 根据 id 删除文章
     *
     * @param id id
     * @return 是否成功
     */
    @DeleteMapping("/{id}")
    public ResponseResult<Object> remove(@PathVariable Long id) {
        articleService.removeById(id);
        return ResponseResult.success();
    }
}
