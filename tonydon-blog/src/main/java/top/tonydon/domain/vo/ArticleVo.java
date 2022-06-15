package top.tonydon.domain.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class ArticleVo {
    private Long id;                // id
    private String title;           // 标题
    private String summary;         // 文章摘要

    private Long categoryId;        // 所属分类id
    private String categoryName;    // 分类名
    private String thumbnail;       // 缩略图
    private Long viewCount;         // 访问量

    private LocalDateTime publishTime;       // 创建时间
    private LocalDateTime updateTime;        // 更新时间
}
