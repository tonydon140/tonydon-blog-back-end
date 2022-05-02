package club.tonydon.domain.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("td_comment")
public class Comment {
    @TableId(type = IdType.AUTO)
    private Long id;            // 评论id
    private String nickname;    // 用户昵称
    private String avatar;      // 用户头像
    private String email;       // 电子邮箱
    private Long articleId;     // 所属文章id
    private Long replyId;      // 回复评论id
    private String content;     // 评论内容

    // 系统数据
    private Date createTime;
    private Integer delFlag;

    @TableField(exist = false)
    private String replyNickname;   // 回复评论用户昵称

    @TableField(exist = false)
    private String articleTitle;    // 回复文章标题
}

