package ru.javawebinar.topjava.service;

import org.springframework.context.annotation.Profile;
import ru.javawebinar.topjava.repository.jdbc.JdbcMealRepository;

@Profile("jdbc")
public class JdbcMealServiceTest extends AbstractMealServiceTest {
    public JdbcMealServiceTest() {
        super(new JdbcMealRepository());
    }
}
