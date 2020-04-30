package com.colinares.tictactoe.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Colinares on 9/15/2018.
 */

public class DateTimeUtils {

    public static String getDateTime() {
        Calendar calendar = Calendar.getInstance(Locale.US);
        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yy HH:mm a", Locale.US);

        return format.format(calendar.getTimeInMillis());
    }
    public static String getDate() {
        Calendar calendar = Calendar.getInstance(Locale.US);
        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy", Locale.US);

        return format.format(calendar.getTimeInMillis());
    }

    public static String getTime() {
        Calendar calendar = Calendar.getInstance(Locale.US);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm a", Locale.US);

        return format.format(calendar.getTimeInMillis());
    }

}
