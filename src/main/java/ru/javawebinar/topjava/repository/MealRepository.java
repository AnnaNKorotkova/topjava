package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalDate;
import java.util.Collection;

public interface MealRepository {
    // null if not found, when updated
    Meal save(int userId, Meal meal);

    // false if not found
    boolean delete(int userId, int mealId);

    // null if not found
    Meal get(int userId, int mealId);

    Collection<Meal> getAll(int userId, LocalDate startDate, LocalDate endDate);
}
