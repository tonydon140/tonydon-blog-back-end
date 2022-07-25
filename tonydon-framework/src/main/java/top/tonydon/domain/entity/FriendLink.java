package top.tonydon.domain.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


/**
 * 友链(Link)表实体类
 *
 * @author makejava
 * @since 2022-03-20 09:17:58
 */
@Data
@TableName("td_friend_link")
public class FriendLink {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;        // 网站名称
    private String logo;        // 网站logo
    private String description; // 网站描述
    private String address;     // 网站地址
    private String status;      // 审核状态 (0代表审核未通过，1代表审核通过，2代表未审核)

    private LocalDateTime createTime;    // 创建时间
    private LocalDateTime checkTime;     // 审核时间
    private String deleted;    // 删除标志（0代表未删除，1代表已删除）
}

