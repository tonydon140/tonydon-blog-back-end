package club.tonydon.controller;

import club.tonydon.constant.PexelsConsts;
import club.tonydon.contant.RedisConsts;
import club.tonydon.domain.ResponseResult;
import club.tonydon.domain.entity.Hour;
import club.tonydon.domain.entity.Image;
import club.tonydon.enums.HttpCodeEnum;
import club.tonydon.exception.SystemException;
import club.tonydon.utils.DateUtils;
import club.tonydon.utils.RedisUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.yaml.snakeyaml.Yaml;


import javax.annotation.Resource;
import java.io.InputStream;
import java.util.Map;
import java.util.Random;

@RestController
public class BlogController {

    @Resource
    private RedisUtils redisUtils;

    private String API_VALUE;

    @GetMapping("/pexels-img")
    public ResponseResult<Object> getPexelsImage() {
        // 1. 获取当前小时的 key
        Hour hour = DateUtils.getHour();
        String nowKey = RedisConsts.PEXELS_IMAGE_PREFIX + hour.getNow();

        // 2. 从 redis 中获取，如果存在直接返回
        String url = redisUtils.getValue(nowKey);
        if (url != null) return ResponseResult.success(url);

        // 3. redis 中不存在，查找上一个小时的图片
        String lastKey = RedisConsts.PEXELS_IMAGE_PREFIX + hour.getLast();
        url = redisUtils.getValue(lastKey);

        // 4. 使用新的线程记录当前小时的图片
        new Thread(()->requestImage(nowKey)).start();

        // 5. 返回
        return ResponseResult.success(url == null ? PexelsConsts.DEFAULT_URL : url);
    }

    private void requestImage(String key){
        // 加载 pexels.yml 读取 API_VALUE 的值
        if (API_VALUE == null){
            Yaml yaml = new Yaml();
            InputStream stream = getClass().getClassLoader().getResourceAsStream("pexels.yml");
            Map<String, String> map = yaml.load(stream);
            API_VALUE = map.get("value");
        }

        // 1. 封装请求头
        HttpHeaders headers = new HttpHeaders();
        headers.add(PexelsConsts.API_KEY, API_VALUE);
        headers.add("user-agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36");
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();

        // 2. 封装 url，请求数据
        String url = PexelsConsts.API + "&page=" + new Random().nextInt(8000);
        Image image = restTemplate.exchange(url, HttpMethod.GET, httpEntity, Image.class).getBody();

        // 3. 判断 image 是否为空
        if(image == null) throw new SystemException(HttpCodeEnum.SYSTEM_ERROR);
        String imageUrl = image.getPhotos().get(0).getSrc().getOriginal() + PexelsConsts.URL_SUFFIX;

        // 4. 存入 redis 中
        redisUtils.setValue(key, imageUrl, RedisConsts.PEXELS_IMAGE_TTL);
    }
}
