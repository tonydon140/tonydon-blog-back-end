package club.tonydon.service;

import club.tonydon.domain.ResponseResult;
import club.tonydon.domain.entity.Comment;
import club.tonydon.domain.vo.CommentVo;
import club.tonydon.domain.vo.PageVo;
import com.baomidou.mybatisplus.extension.service.IService;

public interface CommentService extends IService<Comment> {

    /**
     * 获取评论分页数据
     *
     * @param pageNum  当前页码
     * @param pageSize 页面大小
     * @return PageVo
     */
    ResponseResult<PageVo<CommentVo>> getCommentPage(Integer pageNum, Integer pageSize);

    /**
     * 根据 id 删除评论，如果评论是父评论，会同时删除子评论
     *
     * @param id 评论 id
     * @return 响应数据
     */
    ResponseResult<Object> removeCommentById(Long id);

}
