package top.tonydon.domain.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ArticleVo {
    private Long id;                // id
    private String title;           // 标题
    private String content;         // 文章内容

    private Long categoryId;        // 所属分类id
    private String thumbnail;       // 缩略图
    private Long viewCount;         // 访问量

    private LocalDateTime publishTime;  // 发布时间
    private LocalDateTime updateTime;   // 更新时间

    private String categoryName;    // 分类名称
    private Long commentCount;      // 评论数量
}
