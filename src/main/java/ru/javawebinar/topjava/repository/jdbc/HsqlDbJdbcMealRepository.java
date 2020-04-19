package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.function.Function;

@Profile("hsqldb")
public class HsqlDbJdbcMealRepository extends JdbcMealRepository {

    public HsqlDbJdbcMealRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    protected Timestamp converter(LocalDateTime ldt) {
        return Timestamp.valueOf(ldt);
    }
}
