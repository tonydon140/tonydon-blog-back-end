package club.tonydon.domain.vo;

import lombok.Data;

import java.util.Date;

@Data
public class CommentVo {
    private Long id;
    private String type;
    //文章id
    private Long articleId;
    //评论内容
    private String content;

    private Date createTime;

    // 评论用户名
    private String name;
    // 根评论 id
    private Long rootId;
    // 回复目标评论id
    private Long toCommentId;
}
