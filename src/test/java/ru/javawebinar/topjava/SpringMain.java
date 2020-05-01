package ru.javawebinar.topjava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.GenericXmlApplicationContext;
import ru.javawebinar.topjava.repository.jdbc.JdbcUserRepository;

import javax.validation.Validator;

public class SpringMain {
    @Autowired
    static Validator validator;
    @Autowired
    static JdbcUserRepository jdbcUserRepository;

    public static void main(String[] args) {

        // java 7 automatic resource management
        try (GenericXmlApplicationContext appCtx = new GenericXmlApplicationContext()) {
            appCtx.getEnvironment().setActiveProfiles(Profiles.getActiveDbProfile(), Profiles.REPOSITORY_IMPLEMENTATION);
            appCtx.load("spring/spring-app.xml", "spring/spring-db.xml");
            appCtx.refresh();

            jdbcUserRepository = appCtx.getBean(JdbcUserRepository.class);
        }
    }
}
