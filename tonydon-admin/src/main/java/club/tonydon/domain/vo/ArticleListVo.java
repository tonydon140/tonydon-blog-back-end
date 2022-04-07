package club.tonydon.domain.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ArticleListVo {
    private Long id;
    private String title;           // 标题
    private Long categoryId;        // 所属分类id
    private String categoryName;    // 分类名称
    private String isTop;           // 是否置顶（0否，1是）
    private String status;          // 状态（0已发布，1草稿）
    private Long viewCount;         // 访问量
    private Long createBy;          // 创建人 id
    private String createName;      // 创建人名称
    private Date createTime;        // 创建时间
}
