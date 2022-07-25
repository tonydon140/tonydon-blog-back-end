package top.tonydon.domain.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CategoryVo {
    private Long id;            // id
    private String name;        // 分类名
    private Long pid;           // 父分类id，如果没有父分类为-1
    private String description; // 分类描述
    private String status;      // 状态：0正常, 1禁用

    private Long createBy;
    private LocalDateTime createTime;
    private Long updateBy;
    private LocalDateTime updateTime;

    private String pName;           // 父分类名称
    private String createUsername;  // 创建人用户名
    private String updateUsername;  // 更新人用户名
}
