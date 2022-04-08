package club.tonydon.service.impl;

import club.tonydon.domain.ResponseResult;
import club.tonydon.domain.entity.Comment;
import club.tonydon.domain.vo.CommentVo;
import club.tonydon.domain.vo.PageVo;
import club.tonydon.utils.BeanCopyUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import club.tonydon.mapper.CommentMapper;
import club.tonydon.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;

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

        return null;
    }

    @Override
    public ResponseResult<PageVo<CommentVo>> getCommentList(Long articleId, Long pageNum, Long pageSize) {
        // 1. 封装查询条件
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getArticleId, articleId);

        // 2. 分页查询
        IPage<Comment> iPage = new Page<>(pageNum, pageSize);
        page(iPage, wrapper);

        // 3. 封装数据返回结果
        List<Comment> commentList = iPage.getRecords();
        List<CommentVo> voList = BeanCopyUtils.copyBeanList(commentList, CommentVo.class);
        return ResponseResult.success(new PageVo<>(voList, iPage.getTotal()));
    }
}

