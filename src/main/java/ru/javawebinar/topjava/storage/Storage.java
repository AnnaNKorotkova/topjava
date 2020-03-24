package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public interface Storage {

    void save(Meal meal);

    void update(Meal meal);

    void delete(String uuid);

    Meal get(String uuid);

    CopyOnWriteArrayList<Meal> getAll();
}
