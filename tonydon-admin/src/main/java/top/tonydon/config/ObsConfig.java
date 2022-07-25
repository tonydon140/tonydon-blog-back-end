package top.tonydon.config;

import top.tonydon.constant.ObsConstants;
import com.obs.services.ObsClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * 华为云对象存储服务配置类
 * 将 ObsClient 实例注入 Spring 容器
 */
@Configuration
public class ObsConfig {
    @Bean
    public ObsClient getObsClient() {
        // 从 obs.yml 文件中读取 ak 和 sk
        Yaml yaml = new Yaml();
        InputStream stream = getClass().getClassLoader().getResourceAsStream("obs.yml");
        Map<String, String> map = yaml.load(stream);

        try {
            if (stream != null) {
                stream.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new ObsClient(map.get("ak"), map.get("sk"), ObsConstants.END_POINT);
    }
}
