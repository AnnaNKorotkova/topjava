package ru.javawebinar.topjava.service;

import org.springframework.context.annotation.Profile;
import ru.javawebinar.topjava.repository.jpa.JpaUserRepository;

@Profile("jpa")
public class JpaUserServiceTest extends AbstractUserServiceTest{
    public JpaUserServiceTest() {
        super(new JpaUserRepository());
    }
}
