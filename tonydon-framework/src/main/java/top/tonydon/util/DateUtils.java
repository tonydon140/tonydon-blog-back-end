package top.tonydon.util;

import top.tonydon.domain.entity.Hour;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class DateUtils {

    public static String getToday(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return now.format(formatter);
    }

    /**
     * 根据当前时间返回 Hour 实体
     * @return hour 实例
     */
    public static Hour getHour(){
        Hour hour = new Hour();
        LocalDateTime now = LocalDateTime.now();    // 当前时间
        LocalDateTime last = now.plusHours(-1);     // 前一个小时时间

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-HH");
        hour.setNow(now.format(formatter));
        hour.setLast(last.format(formatter));

        return hour;
    }
}
