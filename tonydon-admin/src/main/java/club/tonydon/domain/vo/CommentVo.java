package club.tonydon.domain.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class CommentVo {

    private Long id;            // 评论id
    private String nickname;    // 用户昵称
    private String avatar;      // 用户头像
    private String email;       // 电子邮箱
    private Long articleId;     // 所属文章id
    private Long replyId;       // 回复评论id
    private String content;     // 评论内容

    // 系统数据
    private Date createTime;
    private Integer delFlag;

    private String replyNickname;   // 回复评论用户昵称
    private String articleTitle;    // 回复文章标题
}
