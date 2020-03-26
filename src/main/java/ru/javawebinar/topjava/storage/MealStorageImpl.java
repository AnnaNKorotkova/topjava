package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class MealStorageImpl implements Storage {

    private Map<Long, Meal> storage = new ConcurrentHashMap<>();
    private AtomicLong lastUuid = new AtomicLong(1L);

    @Override
    public void save(Meal meal) {
        AtomicLong al = new AtomicLong(lastUuid.getAndIncrement());
        meal.setUuid(al);
        storage.put(al.get(), meal);
    }

    @Override
    public void update(Meal meal) {
        storage.replace(meal.getUuid().get(), meal);
    }

    @Override
    public void delete(long uuid) {
        storage.remove(uuid);
    }

    @Override
    public Meal get(long uuid) {
        return storage.get(uuid);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(storage.values());
    }
}