package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;
import ru.javawebinar.topjava.service.UserService;

import java.util.Arrays;
import java.util.Set;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.Profiles.DATAJPA;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;

@ActiveProfiles(DATAJPA)
public class DatajpaUserServiceTest extends AbstractUserServiceTest {

    @Autowired
    private UserService service;

    @Test
    public void findUserMeals() throws Exception {
        Set<Meal> list = service.getAllUserMeals(ADMIN_ID);
        MEAL_MATCHER.assertMatch(list, Arrays.asList(ADMIN_MEAL1, ADMIN_MEAL2));
    }
}
