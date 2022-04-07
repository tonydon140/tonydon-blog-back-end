package club.tonydon.domain.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * 文章表(Article)表实体类
 *
 * @author makejava
 * @since 2022-03-16 13:34:42
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("td_article")
@Accessors(chain = true)    // 设置 setter 返回当前对象本身
public class Article {

    @TableId(type = IdType.AUTO)
    private Long id;
    //标题
    private String title;
    //文章内容
    private String content;
    //文章摘要
    private String summary;
    //所属分类id
    private Long categoryId;

    @TableField(exist = false)
    private String categoryName;    // 分类名称

    //缩略图
    private String thumbnail;
    //是否置顶（0否，1是）
    private String isTop;
    //状态（0已发布，1草稿）
    private String status;
    //访问量
    private Long viewCount;
    //是否允许评论 1是，0否
    private String isComment;
    
    private Long createBy;

    // 创建人名称
    @TableField(exist = false)
    private String createName;

    // 创建时间
    private Date createTime;

    // 更新人 id
    private Long updateBy;

    // 更新人名称
    @TableField(exist = false)
    private String updateName;

    // 更新时间
    private Date updateTime;

    //删除标志（0代表未删除，1代表已删除）
    private Integer delFlag;

}

