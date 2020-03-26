package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicLong;

public class Meal  {

    private AtomicLong uuid;
    private LocalDateTime dateTime;
    private String description;
    private int calories = 0;

    public Meal(AtomicLong uuid, LocalDateTime dateTime, String description, int calories){
        this.uuid = uuid;
        this.dateTime = dateTime;
        this.description = description;
        this.calories=calories;
    }

    public AtomicLong getUuid() {
        return uuid;
    }

    public void setUuid(AtomicLong uuid) {
        this.uuid = uuid;
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
}