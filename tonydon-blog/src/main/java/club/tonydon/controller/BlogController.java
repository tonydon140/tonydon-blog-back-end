package club.tonydon.controller;

import club.tonydon.contant.RedisConsts;
import club.tonydon.domain.ResponseResult;
import club.tonydon.domain.vo.entity.BingImage;
import club.tonydon.utils.DateUtils;
import club.tonydon.utils.RedisUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
public class BlogController {

    @Resource
    private RedisUtils redisUtils;

    @GetMapping("/bing-img")
    public ResponseResult<Object> getBingImage(){
        // 1. 生成当日的 key
        String key = RedisConsts.BING_IMAGE_PREFIX + DateUtils.getToday();
        // 2. 从 redis 中获取，果 redis 中已经存在 url，直接返回
        String url = redisUtils.getValue(key);
        if (url != null) return ResponseResult.success(url);

        // 3. redis 中不存在，请求必应获得 url
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "https://cn.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1";
        BingImage bingImage = restTemplate.getForEntity(apiUrl, BingImage.class).getBody();

        // 4. 如果请求结果为空，返回错误
        if (bingImage == null) return ResponseResult.error();

        // 5. 获取 url，存入 Redis，并返回
        url = "https://www.bing.com" +  bingImage.getImages().get(0).getUrl();
        redisUtils.setValue(key, url, RedisConsts.BING_IMAGE_TTL);
        return ResponseResult.success(url);
    }
}
