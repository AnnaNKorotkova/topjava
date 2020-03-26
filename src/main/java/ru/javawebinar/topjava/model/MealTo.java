package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

public class MealTo {

    private AtomicLong uuid;
    private  LocalDateTime dateTime;
    private  String description;
    private  int calories=0;
    private  boolean excess;

    public MealTo(AtomicLong uuid, LocalDateTime dateTime, String description, int calories, boolean excess){
        this.uuid = uuid;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.excess=excess;
    }

    public boolean getExcess() {
        return excess;
    }

    public AtomicLong getUuid() {
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

    @Override
    public String toString() {
        return "MealTo{" +
                "dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", excess=" + excess +
                '}';
    }
}