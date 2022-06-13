package top.tonydon;

import top.tonydon.utils.DateUtils;
import top.tonydon.utils.RedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class BlogTest {

    @Resource
    private RedisUtils redisUtils;

    @Test
    public void test(){
        System.out.println(DateUtils.getHour());
    }
}
