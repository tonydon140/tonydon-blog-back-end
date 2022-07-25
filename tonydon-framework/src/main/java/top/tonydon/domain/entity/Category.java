package top.tonydon.domain.entity;

import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;



/**
 * 分类表(Category)表实体类
 *
 * @author makejava
 * @since 2022-03-18 16:24:42
 */
@Data
@TableName("td_category")
public class Category {

    private Long id;
    private String name;
    private Long pid;
    private String description;
    private String status;
    
    private Long createBy;
    private LocalDateTime createTime;
    private Long updateBy;
    private LocalDateTime updateTime;

    private String deleted;    // 删除标志（0代表未删除，1代表已删除）
}

