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

    @PostMapping()
    public ResponseResult<Object> addComment(@RequestBody Comment comment) {
        return commentService.saveComment(comment);
    }

    @GetMapping("/{articleId}/{pageNum}/{pageSize}")
    public ResponseResult<PageVo<CommentVo>> getCommentList(
            @PathVariable Long articleId,
            @PathVariable Long pageNum,
            @PathVariable Long pageSize) {
        return commentService.getCommentList(articleId, pageNum, pageSize);
    }
}
