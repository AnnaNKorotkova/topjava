package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);

    private Map<Integer, List<Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(10);

    {
        for (Meal MEAL : MealsUtil.MEALS) {
            save(SecurityUtil.authUserId(), MEAL);
        }
    }

    @Override
    public Meal save(int userId, Meal meal) {

        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        if (repository.containsKey(userId)) {
            repository.get(userId).add(meal);

        } else {
            repository.put(userId, new ArrayList<>(Collections.singletonList(meal)));
        }
        log.info("save {}", meal.getId());
        return meal;
    }

    @Override
    public boolean delete(int userId, int mealId) {
        return repository.get(userId).remove(findIndexById(userId, mealId)) != null;
    }

    @Override
    public Meal get(int userId, int mealId) {
        return repository.get(userId).get(findIndexById(userId, mealId));
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return repository.get(userId).stream()
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    private int findIndexById(int userId, int mealId) {
        List<Meal> list = repository.get(userId);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == mealId) {
                return i;
            }
        }
        throw new NotFoundException("meal id= " + mealId + " did't found");
    }
}
