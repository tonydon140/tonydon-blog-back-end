package top.tonydon.service.impl;

import top.tonydon.domain.vo.CommentVo;
import top.tonydon.domain.ResponseResult;
import top.tonydon.domain.entity.Comment;
import top.tonydon.domain.vo.PageVo;
import top.tonydon.mapper.CommentMapper;
import top.tonydon.service.CommentService;
import top.tonydon.util.AdminCache;
import top.tonydon.util.BeanCopyUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {


    @Resource
    private AdminCache adminCache;

    @Override
    public ResponseResult<PageVo<CommentVo>> findPage(Integer pageNum, Integer pageSize) {
        // 分页查询
        IPage<Comment> iPage = new Page<>(pageNum, pageSize);
        List<Comment> commentList = page(iPage).getRecords();
        List<CommentVo> voList = BeanCopyUtils.copyBeanList(commentList, CommentVo.class);

        // 填充数据
        voList = voList.stream()
                .peek(vo -> {
                    // 填充回复评论昵称
                    if (vo.getReplyId() != -1)
                        vo.setReplyNickname(adminCache.getComment(vo.getReplyId()).getNickname());
                    // 填充回复文章标题
                    vo.setArticleTitle(adminCache.getArticle(vo.getArticleId()).getTitle());
                })
                .collect(Collectors.toList());

        return ResponseResult.success(new PageVo<>(voList, iPage.getTotal()));
    }

    @Override
    public ResponseResult<Object> removeCommentById(Long id) {
        remove(id);
        return ResponseResult.success();
    }


    private void remove(Long id) {
        // 查询子评论
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getReplyId, id);
        List<Comment> commentList = list(wrapper);

        // 递归删除子评论
        for (Comment comment : commentList) remove(comment.getId());

        // 删除当前评论
        removeById(id);
    }

}
