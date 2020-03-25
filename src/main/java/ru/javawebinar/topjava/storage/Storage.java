package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

public interface Storage {

    void save(LocalDateTime ldt, String desc, int cal);

    void update(Meal meal);

    void delete(long uuid);

    Meal get(long uuid);

    List<Meal> getAll();
}
