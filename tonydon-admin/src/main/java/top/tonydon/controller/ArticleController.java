package top.tonydon.controller;

import top.tonydon.domain.ResponseResult;
import top.tonydon.domain.entity.Article;
import top.tonydon.domain.vo.ArticleEditVo;
import top.tonydon.domain.vo.ArticleListVo;
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


    /**
     * 发布文章
     * 如果 id 为空，则发布新文章
     * 如果 id 不为空，则更新草稿文章并发布
     */
    @PostMapping("/publish")
    public ResponseResult<Object> publishArticle(@RequestBody Article article) {
        if (article.getId() == null)
            return articleService.publishNew(article);
        else
            return articleService.publishDraft(article);
    }


    /**
     * 保存草稿文章
     * 如果 id 为空，则保存新文章
     * 如果 id 不为空，则更新文章
     */
    @PostMapping("/draft")
    public ResponseResult<Object> saveDraftArticle(@RequestBody Article article) {
        if (article.getId() == null)
            return ResponseResult.success(articleService.save(article));
        else
            return ResponseResult.success(articleService.updateById(article));
    }


    @GetMapping("/{pageNum}/{pageSize}")
    public ResponseResult<PageVo<ArticleListVo>> findPage(@PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        return articleService.findPage(pageNum, pageSize);
    }

    /**
     * 根据 id 返回文章编辑视图
     *
     * @param id id
     * @return 文章编辑视图
     */
    @GetMapping("/{id}")
    public ResponseResult<ArticleEditVo> find(@PathVariable Long id) {
        return articleService.findById(id);
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
        return articleService.remove(id);
    }
}
