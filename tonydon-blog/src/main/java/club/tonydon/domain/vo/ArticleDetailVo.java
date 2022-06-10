package club.tonydon.domain.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ArticleDetailVo {
    private Long id;                // id
    private String title;           // 标题
    private String content;         // 文章内容
    private String summary;         // 文章摘要
    private Long categoryId;        // 所属分类id
    private String categoryName;    // 分类名
    private String thumbnail;       // 缩略图
    private Long viewCount;         // 访问量
    private Date publishTime;       // 创建时间
    private Date updateTime;        // 更新时间
}
