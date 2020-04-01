package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MealRestController {

    private MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service=service;
    }

    public Meal create( Meal meal) {
        return service.create(SecurityUtil.authUserId(), meal);
    }

    public void update(Meal meal) {
        service.update(SecurityUtil.authUserId(), meal);
    }

    public void delete(int id) {
        service.delete(SecurityUtil.authUserId(), id);
    }

    public Meal get(int id) {
        return service.get(SecurityUtil.authUserId(), id);
    }

    public List<MealTo> getAll(LocalDate startDate, LocalDate endDate) {
        if (startDate == null) {
            startDate = LocalDate.MIN;
        }
        if (endDate == null) {
            endDate = LocalDate.MAX;
        }

        List<Meal> list = new ArrayList<>(service.getAll(SecurityUtil.authUserId(), startDate, endDate));
        return MealsUtil.getTos(list, MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public List<MealTo> getAllByTime(LocalTime startTime, LocalTime endTime, LocalDate startDate, LocalDate endDate) {
        if (startTime == null) {
            startTime = LocalTime.MIN;
        }
        if (endTime == null) {
            endTime = LocalTime.MAX;
        }

        List<Meal> list = new ArrayList<>(service.getAll(SecurityUtil.authUserId(), startDate, endDate));
        return MealsUtil.getFilteredTos(list, MealsUtil.DEFAULT_CALORIES_PER_DAY, startTime, endTime);
    }
}