package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;

public class MealTestData {

    public static final Meal MEAL_1 = new Meal(null, LocalDateTime.parse("2020-04-04T07:00"), "Завтрак", 500);
    public static final Meal MEAL_2 = new Meal(null, LocalDateTime.parse("2020-04-04T13:00"), "Обед", 1000);
    public static final Meal MEAL_3 = new Meal(null, LocalDateTime.parse("2020-04-04T18:00"), "Ужин", 500);
    public static final Meal MEAL_4 = new Meal(null, LocalDateTime.parse("2020-04-05T00:00"), "Ещё хочу", 100);
    public static final Meal MEAL_5 = new Meal(null, LocalDateTime.parse("2020-04-05T07:05"), "Завтрак", 400);
    public static final Meal MEAL_6 = new Meal(null, LocalDateTime.parse("2020-04-05T13:05"), "Обед", 1000);
    public static final Meal MEAL_7 = new Meal(null, LocalDateTime.parse("2020-04-05T18:00"), "Ужин", 500);
    public static final Meal MEAL_8 = new Meal(null, LocalDateTime.parse("2020-04-06T00:00"), "Ещё хочу", 100);
    public static final Meal MEAL_9 = new Meal(null, LocalDateTime.parse("2020-04-04T00:00"), "Админы тоже едят", 1000);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.now(), "newTestMeal", 1001);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(MEAL_1);
        updated.setDateTime(LocalDateTime.parse("2020-04-04T07:01"));
        updated.setDescription("new_Завтрак");
        updated.setCalories(501);
        return updated;
    }
}
