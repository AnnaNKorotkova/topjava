package ru.javawebinar.topjava.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

@ContextConfiguration({
        "classpath:spring/spring-app-for-jdbc.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Integer id = service.getAll(USER_ID).get(0).getId();
        Meal mealDb = service.get(id, USER_ID);
        Meal mealTest = new Meal(MEAL_1);
        mealTest.setId(id);
        Assert.assertEquals(mealDb, mealTest);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() {
        service.get(100, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void getMealOtherUser() {
        Integer id = service.getAll(USER_ID).get(0).getId();
        service.get(id, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void delete() {
        Integer id = service.getAll(USER_ID).get(5).getId();
        service.delete(id, USER_ID);
        service.get(id, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() {
        service.delete(100, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteMealOtherUser() {
        Integer id = service.getAll(USER_ID).get(0).getId();
        service.delete(id, ADMIN_ID);
    }

    @Test
    public void getBetweenHalfOpen() {
        List<Meal> mealsInDataBaseFiltered = service.getBetweenHalfOpen(LocalDate.of(2020, Month.APRIL, 01)
                , LocalDate.of(2020, Month.APRIL, 04), USER_ID);
        List<Meal> mealsInTestDataFiltered = Arrays.asList(new Meal(MEAL_1), new Meal(MEAL_2), new Meal(MEAL_3));
        for (int i = 0; i < mealsInTestDataFiltered.size(); i++) {
            mealsInTestDataFiltered.get(i).setId(START_SEQ + 2 + i);
        }
        Assert.assertEquals(mealsInDataBaseFiltered, mealsInTestDataFiltered);
    }

    @Test
    public void getAll() {
        List<Meal> allInDataBase = service.getAll(USER_ID);
        List<Meal> allInTestData = Arrays.asList(new Meal(MEAL_1), new Meal(MEAL_2), new Meal(MEAL_3),
                new Meal(MEAL_4), new Meal(MEAL_5), new Meal(MEAL_6), new Meal(MEAL_7), new Meal(MEAL_8));
        for (int i = 0; i < allInTestData.size(); i++) {
            allInTestData.get(i).setId(START_SEQ + 2 + i);
        }
        Assert.assertEquals(allInDataBase, allInTestData);
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        Assert.assertEquals(service.get(updated.getId(), USER_ID), updated);
    }

    @Test(expected = NotFoundException.class)
    public void updateNotFound() {
        Meal meal = new Meal(MEAL_1);
        meal.setId(100);
        service.update(meal, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void updateMealOtherUser() {
        Meal meal = service.getAll(USER_ID).get(0);
        service.update(meal, ADMIN_ID);
    }

    @Test
    public void create() {
        Meal newMeal = getNew();
        Meal created = service.create(newMeal, USER_ID);
        Integer newId = created.getId();
        newMeal.setId(newId);
        Assert.assertEquals(created, newMeal);
        Assert.assertEquals(service.get(newId, USER_ID), newMeal);
    }

    @Test(expected = DuplicateKeyException.class)
    public void duplicateDateTimeCreate() {
        service.create(MEAL_1, USER_ID);
    }
}