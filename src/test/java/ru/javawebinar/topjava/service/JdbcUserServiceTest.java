package ru.javawebinar.topjava.service;

import org.springframework.context.annotation.Profile;
import ru.javawebinar.topjava.repository.jdbc.JdbcUserRepository;

@Profile("jdbc")
public class JdbcUserServiceTest extends AbstractUserServiceTest{
    public JdbcUserServiceTest() {
        super(new JdbcUserRepository());
    }
}
