package top.tonydon.domain.entity;

import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 分类表(Category)表实体类
 *
 * @author makejava
 * @since 2022-03-18 16:24:42
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    // 父分类名称
    @TableField(exist = false)
    private String pName;

    // 更新用户名称
    @TableField(exist = false)
    private String updateUsername;

    // 创建用户名称
    @TableField(exist = false)
    private String createUsername;
}

