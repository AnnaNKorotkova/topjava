package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;
import ru.javawebinar.topjava.service.MealService;

import static ru.javawebinar.topjava.Profiles.DATAJPA;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(DATAJPA)
public class DatajpaMealServiceTest extends AbstractMealServiceTest {

    @Autowired
    private MealService service;

    @Test
    public void findUserMeals() throws Exception {
        User user = service.findUserByUserId(ADMIN_ID);
        USER_MATCHER.assertMatch(user, ADMIN);
    }
}
