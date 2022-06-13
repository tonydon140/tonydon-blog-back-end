package top.tonydon.util;

import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class ObsUtils {
    /**
     * 返回 Obs 中存储图片的前缀
     *
     * @return 前缀字符串
     */
    public static String getImageKeyPrefix() {
        Calendar calendar = new GregorianCalendar();
        return "img/" +
                calendar.get(Calendar.YEAR) + '/' +
                (calendar.get(Calendar.MONTH) + 1) + '/' +
                calendar.get(Calendar.DAY_OF_MONTH) + '/';
    }
}
