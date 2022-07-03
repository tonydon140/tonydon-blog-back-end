package top.tonydon;

import top.tonydon.domain.entity.FriendLink;
import top.tonydon.util.DateUtils;
import top.tonydon.util.MailUtils;
import top.tonydon.util.RedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class BlogTest {

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private MailUtils mailUtils;

    @Test
    public void test(){
        FriendLink friendLink = new FriendLink();
        friendLink.setAddress("http://www.tonydon.top");
        friendLink.setName("Tonydon");
        friendLink.setDescription("睡懒觉");
        mailUtils.sendFriendLinkMail(friendLink);
    }
}
