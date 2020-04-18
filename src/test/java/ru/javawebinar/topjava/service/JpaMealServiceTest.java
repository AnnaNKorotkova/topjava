package ru.javawebinar.topjava.service;

import org.springframework.context.annotation.Profile;
import ru.javawebinar.topjava.repository.jpa.JpaMealRepository;

@Profile("jpa")
public class JpaMealServiceTest extends AbstractMealServiceTest{
    public JpaMealServiceTest() {
        super(new JpaMealRepository());
    }
}
