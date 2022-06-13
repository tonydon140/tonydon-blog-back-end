package top.tonydon.service.impl;

import top.tonydon.domain.ResponseResult;
import top.tonydon.domain.entity.Comment;
import top.tonydon.domain.vo.CommentVo;
import top.tonydon.domain.vo.PageVo;
import top.tonydon.utils.BeanCopyUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.tonydon.mapper.CommentMapper;
import top.tonydon.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    @Override
    public ResponseResult<Object> saveComment(Comment comment) {
        comment.setCreateTime(new Date());
        save(comment);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult<PageVo<CommentVo>> getCommentList(Long articleId, Long pageNum, Long pageSize) {
        // 1. 封装查询条件
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getArticleId, articleId);

        // 2. 分页查询
        IPage<Comment> iPage = new Page<>(pageNum, pageSize);
        page(iPage, wrapper);
        List<Comment> commentList = iPage.getRecords();

        // 3. 补全回复评论的昵称
        commentList = commentList.stream().peek(comment -> {
            if(comment.getReplyId() != -1)
                comment.setReplyNickname(getById(comment.getReplyId()).getNickname());
        }).collect(Collectors.toList());

        // 4. 封装成 vo 并返回
        List<CommentVo> voList = BeanCopyUtils.copyBeanList(commentList, CommentVo.class);
        return ResponseResult.success(new PageVo<>(voList, iPage.getTotal()));
    }
}

