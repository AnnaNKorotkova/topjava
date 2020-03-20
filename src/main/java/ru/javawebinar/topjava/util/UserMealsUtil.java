package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410),
                new UserMeal(LocalDateTime.of(2020, Month.FEBRUARY, 1, 8, 0), "Завтрак", 310),
                new UserMeal(LocalDateTime.of(2020, Month.FEBRUARY, 1, 8, 0), "Обед", 800),
                new UserMeal(LocalDateTime.of(2020, Month.FEBRUARY, 1, 13, 0), "Ужин", 610)
        );

        filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000).forEach(System.out::println);
        System.out.println();

        filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000).forEach(System.out::println);
        System.out.println();

        filteredByStreams2(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000).forEach(System.out::println);
        System.out.println();

        filteredByStreams3(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000).forEach(System.out::println);
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExcess> userMealWithExcesses = new ArrayList<>();
        Map<LocalDate, Integer> mapCalPerDay = calPerDay(meals);
        for (UserMeal meal : meals) {
            if (TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                userMealWithExcesses.add(new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), mapCalPerDay.get(meal.getDate()) > caloriesPerDay));
            }
        }
        return userMealWithExcesses;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> mapCalPerDay = calPerDay(meals);
        return meals.stream()
                .filter(s -> TimeUtil.isBetweenHalfOpen(s.getDateTime().toLocalTime(), startTime, endTime))
                .map(s -> new UserMealWithExcess(s.getDateTime(), s.getDescription(), s.getCalories(), mapCalPerDay.get(s.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static Map<LocalDate, Integer> calPerDay(List<UserMeal> meals) {
        Map<LocalDate, Integer> mapCalPerDay = new LinkedHashMap<>();
        meals.forEach(x -> mapCalPerDay.put(x.getDateTime().toLocalDate(), mapCalPerDay.getOrDefault(x.getDateTime().toLocalDate()
                , 0) + x.getCalories()));
        return mapCalPerDay;
    }

    public static List<UserMealWithExcess> filteredByStreams2(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        return meals.stream().collect(Collectors.teeing(
                Collectors.groupingBy(UserMeal::getDate, Collectors.summingInt(UserMeal::getCalories)),
                Collectors.toList(),
                (a, b) ->
                        b.stream()
                                .filter(x -> TimeUtil.isBetweenHalfOpen(x.getDateTime().toLocalTime(), startTime, endTime))
                                .map(x -> new UserMealWithExcess(x.getDateTime(), x.getDescription(), x.getCalories(), a.get(x.getDate()) > caloriesPerDay))
                                .collect(Collectors.toList())));
    }

    public static List<UserMealWithExcess> filteredByStreams3(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
              return  Objects.requireNonNull(meals.stream().collect(Collectors.groupingBy(UserMeal::getDate))
                      .values()
                      .stream().collect(
                              Collectors.toMap(
                                      x -> (x.stream().mapToInt(UserMeal::getCalories).sum() > caloriesPerDay)
                                      , ArrayList::new
                                      , (a, b) -> {
                                          a.addAll(b);
                                          return a;
                                      }))
                      .entrySet().stream()
                      .map(x -> x.getValue().stream().filter(w -> TimeUtil.isBetweenHalfOpen(w.getDateTime().toLocalTime(), startTime, endTime))
                              .map(y -> new UserMealWithExcess(y.getDateTime(), y.getDescription(), y.getCalories(), x.getKey()))
                              .collect(Collectors.toList()))
                      .reduce((x, y) -> {
                          x.addAll(y);
                          return x;
                      }).orElse(null)).stream().sorted().collect(Collectors.toList());
    }
}
