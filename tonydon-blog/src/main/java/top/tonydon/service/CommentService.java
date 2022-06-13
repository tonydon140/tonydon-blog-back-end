package top.tonydon.service;

import top.tonydon.domain.ResponseResult;
import top.tonydon.domain.entity.Comment;
import top.tonydon.domain.vo.CommentVo;
import top.tonydon.domain.vo.PageVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 评论表(Comment)表服务接口
 *
 * @author makejava
 * @since 2022-04-08 17:16:24
 */
public interface CommentService extends IService<Comment> {

    ResponseResult<Object> saveComment(Comment comment);

    ResponseResult<PageVo<CommentVo>> getCommentList(Long articleId, Long pageNum, Long pageSize);
}

