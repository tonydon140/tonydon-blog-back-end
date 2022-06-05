package club.tonydon.domain.vo;

import lombok.Data;

import java.util.Date;

@Data
public class FriendLinkVo {
    private Long id;
    private String name;        // 网站名称
    private String logo;        // 网站logo
    private String description; // 网站描述
    private String address;     // 网站地址
    private String status;      // 审核状态 (0代表审核未通过，1代表审核通过，2代表未审核)
    private Date createTime;    // 创建时间
}
