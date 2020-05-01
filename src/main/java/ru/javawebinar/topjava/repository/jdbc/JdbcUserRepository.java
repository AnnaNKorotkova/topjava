package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepository implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    private final Validator validator;

    {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public User save(User user) {
        if (validator.validate(user).size() > 0) {
            throw new ConstraintViolationException(validator.validate(user));
        }
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
        if (user.isNew()) {
            int newKey = insertUser.executeAndReturnKey(parameterSource).intValue();
            user.setId(newKey);
            jdbcTemplate.batchUpdate("INSERT INTO user_roles (user_id, role) VALUES (?, ?)", new BatchPreparedStatementSetter() {

                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setInt(1, newKey);
                    ps.setString(2, user.getRoles().toArray()[i].toString());
                }

                @Override
                public int getBatchSize() {
                    return user.getRoles().size();
                }
            });
        } else {
            jdbcTemplate.batchUpdate(
                    "UPDATE users SET name=?, email=?, password=?, " +
                            "registered=?, enabled=?, calories_per_day=?  WHERE id=?", new BatchPreparedStatementSetter() {

                        @Override
                        public void setValues(PreparedStatement ps, int i) throws SQLException {
                            ps.setString(1, user.getName());
                            ps.setString(2, user.getEmail());
                            ps.setString(3, user.getPassword());
                            ps.setTimestamp(4, Timestamp.from(user.getRegistered().toInstant()));
                            ps.setBoolean(5, user.isEnabled());
                            ps.setInt(6, user.getCaloriesPerDay());
                            ps.setInt(7, user.getId());
                        }

                        @Override
                        public int getBatchSize() {
                            return 1;
                        }
                    });
        }
        return user;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("SELECT *, (SELECT string_agg (ur.role, ', ') FROM user_roles ur " +
                "WHERE ur.user_id=u.id GROUP BY ur.user_id) as roles FROM users u WHERE u.id = ? ORDER BY name, email ", ROW_MAPPER, id);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public User getByEmail(String email) {
        List<User> users = jdbcTemplate.query("SELECT *, (SELECT string_agg (ur.role, ', ') FROM user_roles ur " +
                "WHERE ur.user_id=u.id GROUP BY ur.user_id) as roles FROM users u WHERE email=?", ROW_MAPPER, email);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("SELECT *, (SELECT string_agg (ur.role, ', ') FROM user_roles ur " +
                "WHERE ur.user_id=u.id GROUP BY ur.user_id) as roles FROM users u ORDER BY name, email", ROW_MAPPER);
    }
}
