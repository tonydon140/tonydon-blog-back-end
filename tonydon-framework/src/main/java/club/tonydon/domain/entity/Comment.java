package club.tonydon.domain.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 评论表(Comment)表实体类
 *
 * @author makejava
 * @since 2022-04-08 17:28:58
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("td_comment")
public class Comment {
    @TableId(type = IdType.AUTO)
    private Long id;
    //评论类型（0代表文章评论，1代表友链评论）
    private String type;
    //文章id
    private Long articleId;
    //评论内容
    private String content;
    
    private Long createBy;
    
    private Date createTime;
    
    private Long updateBy;
    
    private Date updateTime;
    //删除标志（0代表未删除，1代表已删除）
    private Integer delFlag;
    //评论用户名
    private String name;
    //根评论id
    private Long rootId;
    //回复目标评论id
    private Long toCommentId;

}

