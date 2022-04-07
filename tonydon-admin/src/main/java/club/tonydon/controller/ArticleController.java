package club.tonydon.controller;

import club.tonydon.domain.ResponseResult;
import club.tonydon.domain.entity.Article;
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
     * 保存文章
     *
     * @param article 文章
     * @return 响应数据
     */
    @PostMapping
    public ResponseResult<Object> saveArticle(@RequestBody Article article) {
        return articleService.saveArticle(article);
    }

    @PostMapping("/publish")
    public ResponseResult<Object> publishArticle(@RequestBody Article article){
        return articleService.publish(article);
    }


    @PostMapping("/draft")
    public ResponseResult<Object> saveDraftArticle(@RequestBody Article article){
//        System.out.println(article);
        if(article.getId() == null)
            return articleService.saveNewDraft(article);
        return ResponseResult.success();
    }

    @GetMapping
    public ResponseResult<List<ArticleListVo>> getArticleList(){
        return articleService.getArticleList();
    }


}
