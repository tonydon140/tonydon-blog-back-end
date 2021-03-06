package top.tonydon.domain.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class ArticleEditVo {
    private Long id;            // id
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

    private String publishName; // 发布人昵称
    private String updateName;  // 更新人名称
}
