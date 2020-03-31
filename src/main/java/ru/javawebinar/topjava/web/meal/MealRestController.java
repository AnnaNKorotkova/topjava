package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Controller
public class MealRestController {

    @Autowired
    private MealService service;

    public Meal create(int userId, Meal meal) {
        return service.create(userId, meal);
    }

    public void delete(int userId, int id) {
        checkNotFoundWithId(service.delete(userId, id), id);
    }

    public Meal get(int userId, int id) {
        return checkNotFoundWithId(service.get(userId, id), id);
    }

    public List<MealTo> getAll(User user) {
        List<Meal> list = new ArrayList<>(service.getAll(user.getId()));
        if (list.isEmpty()){
            return new ArrayList<>();
        } else {
            return MealsUtil.getTos(list, user.getCaloriesPerDay());
        }
    }

    public List<MealTo> getAllByTime (LocalTime startTime, LocalTime endTime, User user){
        List<Meal> list = new ArrayList<>(service.getAll(user.getId()));
        if (list.isEmpty()){
            return new ArrayList<>();
        } else {
            return MealsUtil.getFilteredTos(list, user.getCaloriesPerDay(),startTime,endTime);
        }
    }
}