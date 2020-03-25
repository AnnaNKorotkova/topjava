package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Meal  {

    private long uuid = 0;
    private LocalDateTime dateTime = LocalDateTime.now();
    private String description=null;
    private int calories = 0;

    public Meal(){
    }

    public Meal(long uuid){
        this.uuid=uuid;
    }

    public Meal(long uuid,LocalDateTime dateTime){
        this(uuid);
        this.dateTime= dateTime;
    }

    public Meal(long uuid, LocalDateTime dateTime, String description){
        this(uuid, dateTime);
        this.description= description;
    }

    public Meal(long uuid, LocalDateTime dateTime, String description, int calories){
        this(uuid, dateTime, description);
        this.calories=calories;
    }

    public long getUuid() {
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
}