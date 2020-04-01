package com.example.magicplacefinder.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    public static String dateToString(Date date){
        DateFormat df = new SimpleDateFormat("HH:mm dd-MM-yy", Locale.ENGLISH);
        return df.format(date);
    }

}
