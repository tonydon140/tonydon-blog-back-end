package club.tonydon.service.impl;

import club.tonydon.contant.SysContants;
import club.tonydon.domain.ResponseResult;
import club.tonydon.domain.entity.Article;
import club.tonydon.domain.vo.ArticleListVo;
import club.tonydon.mapper.ArticleMapper;
import club.tonydon.mapper.CategoryMapper;
import club.tonydon.mapper.UserMapper;
import club.tonydon.service.ArticleService;
import club.tonydon.utils.BeanCopyUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private UserMapper userMapper;


    @Override
    public ResponseResult<Object> saveArticle(Article article) {
        return null;
    }

    /**
     * 发布文章
     *
     * @param article 文章
     * @return 响应数据
     */
    @Override
    public ResponseResult<Object> publish(Article article) {
        // 1. 根据 id 是否存在判断文章是否是新文章
        Long id = article.getId();
        if (id == null) {
            // 2. 写文章
            article.setStatus(SysContants.ARTICLE_STATUS_NORMAL);
        }
        return null;
    }

    @Override
    public ResponseResult<Object> updateDraft(Article article) {
        return null;
    }

    /**
     * 保存新的草稿
     *
     * @param article 草稿文章
     * @return 文章信息
     */
    @Override
    public ResponseResult<Object> saveNewDraft(Article article) {
        // 1. 设置创建时间
        article.setCreateTime(new Date());
        // 2. 保存文章草稿
        boolean save = save(article);
        System.out.println(article.getId());
        return ResponseResult.success(save);
    }

    @Override
    public ResponseResult<List<ArticleListVo>> getArticleList() {
        // 1. 查询所有的文章
        List<Article> articleList = list();

        // 2. 对文章进行处理
        articleList = articleList.stream().peek(article -> {
            article.setCategoryName(categoryMapper.selectById(article.getCategoryId()).getName());
            if(article.getCreateBy() != null)
                article.setCreateName(userMapper.selectById(article.getCreateBy()).getUsername());
            if(article.getUpdateBy() != null)
                article.setUpdateName(userMapper.selectById(article.getUpdateBy()).getUsername());
        }).collect(Collectors.toList());


        // 3. 封装为 vo 对象并返回
        List<ArticleListVo> voList = BeanCopyUtils.copyBeanList(articleList, ArticleListVo.class);
        return ResponseResult.success(voList);
    }

}
