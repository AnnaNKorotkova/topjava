package ru.javawebinar.topjava.service;

import org.springframework.context.annotation.Profile;
import ru.javawebinar.topjava.repository.datajpa.DataJpaMealRepository;

@Profile("datajpa")
public class DatajpaMealServiceTest extends AbstractMealServiceTest {
    public DatajpaMealServiceTest() {
        super(new DataJpaMealRepository());
    }
}
