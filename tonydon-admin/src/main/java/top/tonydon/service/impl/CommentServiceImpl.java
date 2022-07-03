package top.tonydon.service.impl;

import top.tonydon.domain.vo.CommentVo;
import top.tonydon.domain.ResponseResult;
import top.tonydon.domain.entity.Comment;
import top.tonydon.domain.vo.PageVo;
import top.tonydon.mapper.ArticleMapper;
import top.tonydon.mapper.CommentMapper;
import top.tonydon.service.CommentService;
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
    private ArticleMapper articleMapper;

    @Override
    public ResponseResult<PageVo<CommentVo>> getCommentPage(Integer pageNum, Integer pageSize) {
        // 分页查询
        IPage<Comment> iPage = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Comment::getCreateTime);
        page(iPage, wrapper);
        List<Comment> commentList = iPage.getRecords();

        // 填充数据
        List<Comment> list = commentList.stream()
                .peek(comment -> {
                    // 填充回复评论昵称
                    if (comment.getReplyId() != -1)
                        comment.setReplyNickname(getById(comment.getReplyId()).getNickname());
                    // 填充回复文章标题
                    comment.setArticleTitle(articleMapper.selectById(comment.getArticleId()).getTitle());
                })
                .collect(Collectors.toList());

        // Bean 拷贝
        List<CommentVo> voList = BeanCopyUtils.copyBeanList(list, CommentVo.class);

        return ResponseResult.success(new PageVo<>(voList, iPage.getTotal()));
    }

    @Override
    public ResponseResult<Object> removeCommentById(Long id) {
        remove(id);
        return ResponseResult.success();
    }


    private void remove(Long id){
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
