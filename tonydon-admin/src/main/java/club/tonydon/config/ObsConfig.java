package club.tonydon.config;

import club.tonydon.contant.ObsConsts;
import com.obs.services.ObsClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 华为云对象存储服务配置类
 * 将 ObsClient 实例注入 Spring 容器
 */
@Configuration
public class ObsConfig {
    @Bean
    public ObsClient getObsClient() {
        return new ObsClient(ObsConsts.AK, ObsConsts.SK, ObsConsts.END_POINT);
    }
}
