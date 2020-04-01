package com.example.magicplacefinder.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtils {

    public static String dateToString(Date date){
        DateFormat df = new SimpleDateFormat("HH:mm dd-MM-yy", Locale.ENGLISH);
        return df.format(date);
    }

    public static int dayFromDate(Date date){
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }
}
