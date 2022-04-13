package club.tonydon;

import club.tonydon.utils.DateUtils;
import club.tonydon.utils.RedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.GregorianCalendar;

@SpringBootTest
public class BlogTest {

    @Resource
    private RedisUtils redisUtils;

    @Test
    public void test(){
        System.out.println(DateUtils.getHour());
    }
}
