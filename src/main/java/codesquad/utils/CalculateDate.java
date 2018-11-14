package codesquad.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CalculateDate {
    public static String getToday() {
        Date today = new Date();
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd ");
        SimpleDateFormat time = new SimpleDateFormat("HH:mm");
        return date.format(today) + time.format(today);
    }
}
