package ru.javawebinar.topjava.util;

import org.springframework.format.Formatter;
import org.springframework.lang.Nullable;

import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LocalTimeFormatter implements Formatter<LocalTime> {

    private String pattern;

    public LocalTimeFormatter(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public LocalTime parse(@Nullable String text, Locale locale) throws ParseException {

        if (text == null || text.length() == 0) {
            return null;
        }
        return LocalTime.parse(text, DateTimeFormatter.ofPattern(this.pattern));
    }

    @Override
    public String print(LocalTime object, Locale locale) {
        return null;
    }
}
