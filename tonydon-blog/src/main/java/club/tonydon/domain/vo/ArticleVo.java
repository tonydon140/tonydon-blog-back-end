package club.tonydon.domain.vo;

import lombok.Data;

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
    private Date createTime;        // 创建时间
}
