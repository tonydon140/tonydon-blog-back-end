package top.tonydon.domain.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ArticleListVo {
    private Long id;
    private String title;           // 标题
    private Long categoryId;        // 所属分类id
    private String categoryName;    // 分类名称
    private Long viewCount;         // 访问量

    private String isTop;       // 是否置顶（0否，1是）
    private String isPublish;   // 是否发布（0草稿，1草稿）
    private String isComment;   // 是否允许评论（0禁止，1允许）

    private Long publishBy;         // 创建人 id
    private Date publishTime;       // 创建时间
    private String publishName;     // 创建人名称

    private Long updateBy;      // 更新人 id
    private Date updateTime;    // 更新时间
    private String updateName;  // 更新人名称
}
