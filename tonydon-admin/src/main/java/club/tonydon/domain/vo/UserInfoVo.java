package club.tonydon.domain.vo;

import lombok.Data;

@Data
public class UserInfoVo {
    private Long id;
    private String nickname;
    private String username;
    private String avatar;
    private String sex;
    private String email;
}
