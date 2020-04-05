package ru.javawebinar.topjava.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@ExtendWith(SpringExtension.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    void get() {
        Integer id = service.getAll(USER_ID).get(0).getId();
        Meal meal = service.get(id, USER_ID);
        assertMatch(meal, MEAL_1);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> {
        service.get(100, USER_ID);
        });
    }

    @Test
    void getMealOtherUser() {
        assertThrows(NotFoundException.class, () -> {
            Integer id = service.getAll(USER_ID).get(0).getId();
            service.get(id, ADMIN_ID);
        });
    }

    @Test
    void delete() {
        assertThrows(NotFoundException.class, () -> {
            Integer id = service.getAll(USER_ID).get(5).getId();
            service.delete(id, USER_ID);
            service.get(id, USER_ID);
        });
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> {
            service.delete(100, USER_ID);
        });
    }

    @Test
    void deleteMealOtherUser() {
        assertThrows(NotFoundException.class, () -> {
            Integer id = service.getAll(USER_ID).get(0).getId();
            service.delete(id, ADMIN_ID);
        });
    }

    @Test
    void getBetweenHalfOpen() {
        List<Meal> mealsInDataBaseFiltered = service.getBetweenHalfOpen(LocalDate.of(2020, Month.APRIL, 01)
                , LocalDate.of(2020, Month.APRIL, 04), USER_ID);
        List<Meal> mealsInTestDataFiltered = Arrays.asList(MEAL_1, MEAL_2, MEAL_3);
        assertMatch(mealsInDataBaseFiltered, mealsInTestDataFiltered);
    }

    @Test
    void getAll() {
        List<Meal> allInDataBase = service.getAll(USER_ID);
        List<Meal> allInTestData = Arrays.asList(MEAL_1, MEAL_2, MEAL_3, MEAL_4, MEAL_5, MEAL_6, MEAL_7, MEAL_8);
        assertMatch(allInDataBase, allInTestData);
    }

    @Test
    void update() {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        assertEquals(service.get(updated.getId(), USER_ID), updated);
    }

    @Test
    void updateNotFound() {
        assertThrows(NotFoundException.class, () -> {
            Meal meal = new Meal(MEAL_1);
            meal.setId(100);
        service.update(meal, USER_ID);
        });
    }

    @Test
    void updateMealOtherUser() {
        assertThrows(NotFoundException.class, () -> {
            Meal meal= service.getAll(USER_ID).get(0);
            service.update(meal, ADMIN_ID);
        });
    }

    @Test
    void create() {
        Meal newMeal = getNew();
        Meal created = service.create(newMeal, USER_ID);
        Integer newId = created.getId();
        newMeal.setId(newId);
        assertEquals(created, newMeal);
        assertEquals(service.get(newId, USER_ID), newMeal);
    }

    @Test
    public void duplicateDateTimeCreate() {
        assertThrows(DuplicateKeyException.class, () -> {
            service.create(MEAL_1, USER_ID);
        });
    }
}