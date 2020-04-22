package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.javawebinar.topjava.model.User;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Profile("hsqldb")
public class HsqlDbJdbcMealRepository extends AbstractJdbcMealRepository<Timestamp> {

    public HsqlDbJdbcMealRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    protected Timestamp converter(LocalDateTime ldt) {
        return Timestamp.valueOf(ldt);
    }

    @Override
    public User findUserByUserId(int userId) {
        return null;
    }
}
