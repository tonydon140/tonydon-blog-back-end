package club.tonydon.utils;

import club.tonydon.domain.entity.Hour;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class DateUtils {

    public static String getToday(){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        return format.format(new Date());
    }

    /**
     * 根据当前时间返回 Hour 实体
     * @return hour 实例
     */
    public static Hour getHour(){
        SimpleDateFormat format = new SimpleDateFormat("MM-dd-HH");
        Calendar calendar = new GregorianCalendar();

        Hour hour = new Hour();
        hour.setNow(format.format(calendar.getTime()));
        calendar.add(Calendar.HOUR_OF_DAY, -1);
        hour.setLast(format.format(calendar.getTime()));

        return hour;
    }
}
