package club.tonydon;

import com.obs.services.ObsClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import javax.annotation.Resource;
import java.io.IOException;

@SpringBootTest
public class OBSTest {

    @Resource
    ObsClient obsClient;

    @Test
    public void test() throws IOException {
        System.out.println(obsClient);
    }

}
