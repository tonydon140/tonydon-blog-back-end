package club.tonydon.utils;

import java.text.SimpleDateFormat;
import java.util.Date;


public class DateUtils {

    public static String getToday(){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        return format.format(new Date());
    }
}
