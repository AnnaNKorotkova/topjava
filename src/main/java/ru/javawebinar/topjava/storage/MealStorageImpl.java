package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MealStorageImpl implements Storage {

    private Map<Long, Meal> storage = new ConcurrentHashMap<>();
    private long lastUuid = 0L;

    @Override
    public void save(LocalDateTime ldt, String desc, int cal) {
        storage.put(++lastUuid, new Meal(lastUuid, ldt, desc, cal));
    }

    @Override
    public void update(Meal meal) {
        storage.replace(meal.getUuid(), meal);
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