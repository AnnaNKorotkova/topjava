package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;

public class MealTo {

    private  long uuid = 0;

    private  LocalDateTime dateTime= LocalDateTime.now();

    private  String description =  null;

    private  int calories=0;

    private  boolean excess= true;

    public MealTo(long uuid){
        this.uuid=uuid;
    }
    public MealTo(long uuid, LocalDateTime dateTime){
        this(uuid);
        this.dateTime=dateTime;
    }
    public MealTo(long uuid, LocalDateTime dateTime, String description){
        this(uuid, dateTime);
        this.description=description ;
    }

    public MealTo(long uuid, LocalDateTime dateTime, String description, int calories){
        this(uuid, dateTime, description);
        this.calories=calories;
    }

    public MealTo(long uuid, LocalDateTime dateTime, String description, int calories, boolean excess){
        this(uuid, dateTime, description, calories);
        this.excess=excess;
    }

    public boolean getExcess() {
        return excess;
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