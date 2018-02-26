package me.karanthaker.traveljournal.me.karanthaker.traveljournal.helpers;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by KTHAKER on 26/02/2018.
 */

public class Converters {

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

}