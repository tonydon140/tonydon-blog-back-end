package top.tonydon.controller;

import top.tonydon.domain.ResponseResult;
import top.tonydon.domain.vo.CommentVo;
import top.tonydon.domain.vo.PageVo;
import top.tonydon.service.CommentService;
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
    public ResponseResult<PageVo<CommentVo>> findPage(@PathVariable Integer pageNum, @PathVariable Integer pageSize){
        return commentService.findPage(pageNum, pageSize);
    }

    @DeleteMapping("/{id}")
    public ResponseResult<Object> removeComment(@PathVariable Long id){
        return commentService.removeCommentById(id);
    }

}
