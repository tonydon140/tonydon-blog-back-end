package club.tonydon.controller;

import club.tonydon.constant.ObsConsts;
import club.tonydon.domain.ResponseResult;
import club.tonydon.enums.HttpCodeEnum;
import club.tonydon.util.FileUtils;
import club.tonydon.util.ObsUtils;
import cn.hutool.core.lang.UUID;
import com.obs.services.ObsClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

@RestController
@RequestMapping("/upload")
public class FileController {

    @Resource
    private ObsClient obsClient;

    /**
     * 上传图片
     *
     * @param file 图片文件
     * @return 图片 url
     */
    @PostMapping("/img")
    public ResponseResult<Object> uploadImage(MultipartFile file) throws IOException {
        // 1. 获取文件后缀名
        String suffix = FileUtils.getSuffix(file);

        // 2. 判断文件是否是图片类型
        if(!FileUtils.isImage(suffix))
            return ResponseResult.error(HttpCodeEnum.NOT_IMAGE_ERROR);

        // 3. 上传图片名称和存储路径
        String obsFilename = UUID.randomUUID().toString(true) + suffix;
        String objectKey = ObsUtils.getImageKeyPrefix() + obsFilename;

        // 4. 上传文件
        obsClient.putObject(ObsConsts.BUCKET_NAME, objectKey, file.getInputStream());

        // 5. 返回文件上传后的访问地址
        return ResponseResult.success(ObsConsts.DOMAIN_NAME + objectKey);
    }


}
