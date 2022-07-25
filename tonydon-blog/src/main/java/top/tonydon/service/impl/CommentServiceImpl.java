package top.tonydon.service.impl;

import top.tonydon.constant.BlogRedisConstants;
import top.tonydon.domain.ResponseResult;
import top.tonydon.domain.entity.Article;
import top.tonydon.domain.entity.Comment;
import top.tonydon.domain.vo.ArticleVo;
import top.tonydon.domain.vo.CommentVo;
import top.tonydon.domain.vo.PageVo;
import top.tonydon.enums.HttpCodeEnum;
import top.tonydon.mapper.ArticleMapper;
import top.tonydon.util.BeanCopyUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.tonydon.mapper.CommentMapper;
import top.tonydon.service.CommentService;
import org.springframework.stereotype.Service;
import top.tonydon.util.BlogCache;
import top.tonydon.util.MailUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2022-04-08 17:16:24
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Resource
    private MailUtils mailUtils;

    @Resource
    private BlogCache blogCache;

    @Override
    public ResponseResult<PageVo<CommentVo>> getCommentList(Long articleId, Long pageNum, Long pageSize) {
        // 1. 封装查询条件
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getArticleId, articleId);

        // 2. 分页查询
        IPage<Comment> iPage = new Page<>(pageNum, pageSize);
        page(iPage, wrapper);
        List<Comment> commentList = iPage.getRecords();
        List<CommentVo> voList = BeanCopyUtils.copyBeanList(commentList, CommentVo.class);

        // 3. 补全回复评论的昵称
        voList = voList.stream().peek(vo -> {
            if (vo.getReplyId() != -1)
                // 从缓存中查询评论
                vo.setReplyNickname(blogCache.getComment(vo.getReplyId()).getNickname());
        }).collect(Collectors.toList());

        // 4. 封装成 vo 并返回
        return ResponseResult.success(new PageVo<>(voList, iPage.getTotal()));
    }

    @Override
    public ResponseResult<Object> addOne(Comment comment) {
        // 查询评论的文章
        Long articleId = comment.getArticleId();
        ArticleVo articleVo = blogCache.getArticleVo(articleId);
        if (articleVo == null)
            return ResponseResult.error(HttpCodeEnum.ARTICLE_NOT_EXIST);

        // 保存评论
        save(comment);

        // 删除对应的文章缓存、文章评论总数缓存
        blogCache.delete(BlogRedisConstants.CACHE_ARTICLE_KEY + articleId);
        blogCache.delete(BlogRedisConstants.CACHE_ARTICLE_COMMENT_COUNT_KEY + articleId);

        // 开启新的线程发送邮件
        new Thread(() -> mailUtils.sendCommentMail(comment.getContent(), articleVo.getTitle(), articleId)).start();
        return ResponseResult.success();
    }
}

