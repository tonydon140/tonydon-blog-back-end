package club.tonydon;

import club.tonydon.contant.RedisConsts;
import club.tonydon.domain.vo.entity.BingImage;
import club.tonydon.utils.DateUtils;
import club.tonydon.utils.RedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@SpringBootTest
public class BlogTest {

    @Resource
    private RedisUtils redisUtils;

    @Test
    public void test(){
    }
}
