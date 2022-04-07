package club.tonydon.domain.vo;

import lombok.Data;

@Data
public class HotArticleVo {
    private Long id;            // ID
    private String title;       // 标题
    private Long viewCount;     // 访问量
}
