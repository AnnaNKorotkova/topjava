package ru.javawebinar.topjava.util;

import org.springframework.format.FormatterRegistrar;
import org.springframework.format.FormatterRegistry;

public class CustomFormatterRegistrar implements FormatterRegistrar {
    @Override
    public void registerFormatters(FormatterRegistry registry) {
        registry.addFormatter(new LocalDateFormatter("yyyy-MM-dd"));
        registry.addFormatter(new LocalTimeFormatter("HH:mm"));
//      registry.addFormatter(new DateFormatter("yyyy-MM-dd"));
    }
}
