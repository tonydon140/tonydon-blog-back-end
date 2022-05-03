package club.tonydon;

import club.tonydon.domain.entity.Article;
import club.tonydon.mapper.CategoryMapper;
import club.tonydon.mapper.UserMapper;
import club.tonydon.service.ArticleService;
import com.obs.services.ObsClient;
import com.obs.services.model.ObsBucket;
import com.obs.services.model.ObsObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import javax.annotation.Resource;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@SpringBootTest
public class OBSTest {

    @Resource
    ObsClient obsClient;

    @Test
    public void test() throws IOException {
        System.out.println(obsClient);
    }

}
