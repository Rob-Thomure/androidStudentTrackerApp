package com.robertthomure.rt_mob_app_proj2.Database;

import androidx.room.TypeConverter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateConverters {

    @TypeConverter
    public static LocalDate longToLocalDate(Long value) {
        return LocalDate.ofEpochDay(value);
    }

    @TypeConverter
    public static Long localDateToLong(LocalDate value) {
        return value.toEpochDay();
    }

    public static LocalDate stringToLocalDate(String value) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return LocalDate.parse(value, formatter);
    }

    public static String localDateToString(LocalDate value) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return value.format(formatter);
    }

    public static Long localDateToLongEpochMilli(LocalDate localDate) {
        LocalDateTime localDateTime = localDate.atStartOfDay();
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        return instant.toEpochMilli();
    }
}
