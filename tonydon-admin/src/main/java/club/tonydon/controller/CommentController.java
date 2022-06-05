package club.tonydon.controller;

import club.tonydon.domain.ResponseResult;
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
     * pageNum    当前页码
     * pageSize   页面大小
     */
    @GetMapping("/{pageNum}/{pageSize}")
    public ResponseResult<PageVo<CommentVo>> getCommentPage(@PathVariable Integer pageNum,
                                                            @PathVariable Integer pageSize){
        return commentService.getCommentPage(pageNum, pageSize);
    }

    @DeleteMapping("/{id}")
    public ResponseResult<Object> removeComment(@PathVariable Long id){
        return commentService.removeCommentById(id);
    }

}
