package jieyun.jjlink.com.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by lu on 2015/6/25.
 */
public class ToolUtils {
    public static String parseTime(int time) {
        System.setProperty("user.timezone", "Asia/Shanghai");
        TimeZone tz = TimeZone.getTimeZone("Asia/Shanghai");
        TimeZone.setDefault(tz);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        Date date = new Date(time * 1000l);
        return format.format(date);
    }
}
