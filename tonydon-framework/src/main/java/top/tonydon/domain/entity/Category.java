package top.tonydon.domain.entity;

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

    @TableId(type = IdType.AUTO)
    private Long id;
    // 分类名
    private String name;

    // 父分类id，如果没有父分类为-1
    private Long pid;

    // 父分类名称
    @TableField(exist = false)
    private String pName;

    //描述
    private String description;

    //状态0:正常,1禁用
    private String status;
    
    private Long createBy;

    // 创建用户名称
    @TableField(exist = false)
    private String createUsername;
    
    private Date createTime;
    
    private Long updateBy;

    // 更新用户名称
    @TableField(exist = false)
    private String updateUsername;
    
    private Date updateTime;
    //删除标志（0代表未删除，1代表已删除）
    private Integer delFlag;
}

