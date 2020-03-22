package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;

public class MealStorageImpl implements Storage {

    private List<Meal> storage = new ArrayList<>();

    public void setStorage(List<Meal> storage) {
        this.storage = storage;
    }

    @Override
    public void save(Meal meal) {
        storage.add(meal);
    }

    @Override
    public void update(Meal meal) {
        storage.set(findIndexByUuid(meal.getUuid()), meal);
    }

    @Override
    public void delete(Meal meal) {
        storage.remove(meal);
    }

    @Override
    public Meal get(String uuid) {
        return storage.get(findIndexByUuid(uuid));
    }

    @Override
    public List<Meal> getAll() {
        return storage;
    }

    protected Integer findIndexByUuid(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (uuid.equals(storage.get(i).getUuid())) {
                return i;
            }
        }
        return -1;
    }
}
