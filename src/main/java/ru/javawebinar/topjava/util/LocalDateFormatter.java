package ru.javawebinar.topjava.util;

import org.springframework.format.Formatter;
import org.springframework.lang.Nullable;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LocalDateFormatter implements Formatter<LocalDate> {

    private String pattern;

    public LocalDateFormatter(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public LocalDate parse(@Nullable String text, Locale locale) throws ParseException {

        if (text == null || text.length() == 0) {
            return null;
        }
        return LocalDate.parse(text, DateTimeFormatter.ofPattern(this.pattern));
    }

    @Override
    public String print(LocalDate object, Locale locale) {
        return null;
    }

}
