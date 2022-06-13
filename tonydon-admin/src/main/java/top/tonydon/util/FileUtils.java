package top.tonydon.util;

import top.tonydon.enums.HttpCodeEnum;
import top.tonydon.exception.SystemException;
import cn.hutool.core.util.StrUtil;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.Set;

public class FileUtils {

    private static final Set<String> imageSuffix = new HashSet<>();

    static {
        imageSuffix.add(".jpg");
        imageSuffix.add(".jpeg");
        imageSuffix.add(".png");
    }

    /**
     * 获取文件的后缀名
     *
     * @param file 文件
     * @return 文件后缀名
     */
    public static String getSuffix(MultipartFile file) {
        // 1. 判断文件是否未空
        if (file.isEmpty())
            throw new SystemException(HttpCodeEnum.FILE_EMPTY_ERROR);

        // 2. 获取文件后缀名，如果文件名未空，抛出异常
        String filename = file.getOriginalFilename();
        if (StrUtil.isEmpty(filename))
            throw new SystemException(HttpCodeEnum.NO_FILENAME_ERROR);
        String[] split = filename.split("\\.");
        return "." + split[split.length - 1];
    }


    /**
     * 根据文件后缀名判断文件是否是图片类型
     *
     * @param suffix 文件后缀名，例如：.png, .jpg
     * @return 是否是图片类型
     */
    public static boolean isImage(String suffix) {
        return imageSuffix.contains(suffix);
    }
}
