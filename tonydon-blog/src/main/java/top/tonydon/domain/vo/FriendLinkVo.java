package top.tonydon.domain.vo;

import lombok.Data;

@Data
public class FriendLinkVo {
    private Long id;            // id
    private String name;        // 名称
    private String logo;        // logo 图片地址
    private String description; // 网址描述
    private String address;     // 网址地址
}
