package club.tonydon.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class BeanCopyUtils {
    private BeanCopyUtils() {

    }

    // 拷贝 Bean
    public static <T> T copyBean(Object source, Class<T> clazz) {
        T result = null;
        try {
            // 创建目标对象
            result = clazz.newInstance();
            // 实现拷贝
            BeanUtils.copyProperties(source, result);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;
    }

    // 复制列表
    public static <O, T> List<T> copyBeanList(List<O> list, Class<T> clazz) {
        return list.stream()
                .map(o -> copyBean(o, clazz))
                .collect(Collectors.toList());
    }
}
