package ru.javawebinar.topjava.service;

import org.springframework.context.annotation.Profile;
import ru.javawebinar.topjava.repository.datajpa.DataJpaUserRepository;

@Profile("datajpa")
public class DatajpaUserServiceTest extends AbstractUserServiceTest{
    public DatajpaUserServiceTest() {
        super(new DataJpaUserRepository());
    }
}
