package com.botnerd.core.util.datetime;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author tjudkins
 * @since 2/13/17
 */

public class DateTimeUtils {
    public static Date tryParse(SimpleDateFormat[] sdfs, String dateString)
    {
        if (null == sdfs || TextUtils.isEmpty(dateString)) {
            return null;
        }
        for (SimpleDateFormat sdf : sdfs) {
            try
            {
                return sdf.parse(dateString);
            }
            catch (ParseException ignored) {}
        }
        return null;
    }

}
