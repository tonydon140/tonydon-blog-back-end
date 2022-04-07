package club.tonydon.domain.vo;

import lombok.Data;

import java.util.Date;

@Data
public class CategoryVo {
    private Long id;
    private String name;        // 分类名
    private Long pid;           // 父分类id，如果没有父分类为-1
    private String pName;       // 父分类名称
    private String description;
    private String status;      // 状态：0正常, 1禁用
    private Long createBy;
    private String createUsername;
    private Date createTime;
    private Long updateBy;
    private String updateUsername;
    private Date updateTime;
}
