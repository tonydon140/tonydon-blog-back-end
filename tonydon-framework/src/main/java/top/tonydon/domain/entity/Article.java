package top.tonydon.domain.entity;

import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * 文章表(Article)表实体类
 *
 * @author makejava
 * @since 2022-03-16 13:34:42
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("td_article")
@Accessors(chain = true)    // 设置 setter 返回当前对象本身
public class Article {

    private Long id;
    private String title;       // 标题
    private String content;     // 文章内容
    private String summary;     // 文章摘要
    private Long categoryId;    // 所属分类id
    private String thumbnail;   // 缩略图
    private Long viewCount;     // 访问量

    private String isTop;       // 是否置顶（0否，1是）
    private String isPublish;   // 是否发布（0草稿，1草稿）
    private String isComment;   // 是否允许评论（0禁止，1允许）

    private Long publishBy;              // 发布人id
    private LocalDateTime publishTime;   // 发布时间
    private Long updateBy;               // 更新人id
    private LocalDateTime updateTime;    // 更新时间

    private String deleted;    // 删除标志（0代表未删除，1代表已删除）

    // 分类名称
    @TableField(exist = false)
    private String categoryName;

    // 更新人名称
    @TableField(exist = false)
    private String updateName;

    // 发布人名称
    @TableField(exist = false)
    private String publishName;

    @TableField(exist = false)
    private Long commentCount;
}

