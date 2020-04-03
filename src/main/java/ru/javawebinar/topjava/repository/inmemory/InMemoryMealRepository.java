package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.ValidationUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);

    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        for (Meal MEAL : MealsUtil.MEALS) {
            save(1, MEAL);
        }
    }

    @Override
    public Meal save(int userId, Meal meal) {

        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }

        repository.computeIfAbsent(userId, key -> new ConcurrentHashMap<>()).put(meal.getId(), meal);
        log.info("save {}", meal.getId());
        return meal;
    }

    @Override
    public boolean delete(int userId, int mealId) {
        return repository.get(userId).remove(mealId) != null;
    }

    @Override
    public Meal get(int userId, int mealId) {
        return repository.get(userId).get(mealId);
    }

    @Override
    public Collection<Meal> getAll(int userId) {

        return repository.get(userId) == null ? new ArrayList<>() : getStream(userId)
                        .collect(Collectors.toList());
    }

    @Override
    public Collection<Meal> getAllFiltered(int userId, LocalDate startDate, LocalDate endDate) {

        return repository.get(userId) == null ? new ArrayList<>() : getStream(userId)
                        .filter(meal -> DateTimeUtil.isBetweenInclusive(meal.getDate(), startDate, endDate))
                        .collect(Collectors.toList());
    }
    private Stream<Meal> getStream(int userId){
        return repository.get(userId).values().stream()
                        .sorted(Comparator.comparing(Meal::getDateTime).reversed());
    }
}
