package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;

@Profile("postgres")
public class PostgresJdbcMealRepository extends AbstractJdbcMealRepository<LocalDateTime> {

    public PostgresJdbcMealRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    protected LocalDateTime converter(LocalDateTime ldt) {
        return ldt;
    }

    @Override
    public User findUserByUserId(int userId) {
        return null;
    }
}
