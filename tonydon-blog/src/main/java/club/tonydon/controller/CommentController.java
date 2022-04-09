package club.tonydon.controller;

import club.tonydon.domain.ResponseResult;
import club.tonydon.domain.entity.Comment;
import club.tonydon.domain.vo.CommentVo;
import club.tonydon.domain.vo.PageVo;
import club.tonydon.service.CommentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Resource
    private CommentService commentService;

    /**
     * 添加一条评论
     *
     * @param comment 评论实体
     * @return 响应数据
     */
    @PostMapping()
    public ResponseResult<Object> addComment(@RequestBody Comment comment) {
        return commentService.saveComment(comment);
    }


    /**
     * 获取某文章下的分页评论
     *
     * @param articleId 文章 id
     * @param pageNum   当前分页
     * @param pageSize  页面大小
     * @return 评论分页
     */
    @GetMapping("/{articleId}/{pageNum}/{pageSize}")
    public ResponseResult<PageVo<CommentVo>> getCommentList(
            @PathVariable Long articleId,
            @PathVariable Long pageNum,
            @PathVariable Long pageSize) {
        return commentService.getCommentList(articleId, pageNum, pageSize);
    }
}
