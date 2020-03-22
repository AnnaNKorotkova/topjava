package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

public class Meal implements Comparable<Meal> {
    private final String uuid;

    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this.uuid = UUID.randomUUID().toString();
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }
    public Meal(String uuid, LocalDateTime dateTime, String description, int calories) {
        this.uuid = uuid;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public String getUuid() {
        return uuid;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    @Override
    public int compareTo(Meal meal) {
        return uuid.compareTo(meal.getUuid());
    }
}
