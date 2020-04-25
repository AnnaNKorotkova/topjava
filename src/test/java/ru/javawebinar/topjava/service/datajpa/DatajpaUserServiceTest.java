package ru.javawebinar.topjava.service.datajpa;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.Profiles.DATAJPA;
import static ru.javawebinar.topjava.UserTestData.ADMIN;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;

@ActiveProfiles(DATAJPA)
public class DatajpaUserServiceTest extends AbstractUserServiceTest {

    @Autowired
    private UserService service;

    @Test
    public void getUserWithMeals() throws Exception {
        User user = service.getUserWithMeals(ADMIN_ID);
        Assert.assertEquals(user, ADMIN);
    }

    @Test
    public void notFoundUserWithMeals() throws Exception {
        Assert.assertThrows(NotFoundException.class, () ->
                service.getUserWithMeals(ADMIN_ID + 100)
        );
    }
}
