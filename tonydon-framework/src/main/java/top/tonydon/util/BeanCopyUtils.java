package top.tonydon.util;

import org.springframework.beans.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BeanCopyUtils {
    private BeanCopyUtils() {

    }

    // 拷贝 Bean
    public static <T> T copyBean(Object source, Class<T> clazz) {
        T result = null;
        try {
            // 创建目标对象
            result = clazz.getDeclaredConstructor().newInstance();
            // 实现拷贝
            BeanUtils.copyProperties(source, result);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    // 复制列表
    public static <O, T> List<T> copyBeanList(List<O> list, Class<T> clazz) {
        return list.stream()
                .map(o -> copyBean(o, clazz))
                .collect(Collectors.toList());
    }

    public static Map<String, Object> beanToMap(Object bean){
        return null;
    }
}
