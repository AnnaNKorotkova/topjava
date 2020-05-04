package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class UserExtractor implements ResultSetExtractor<List<User>> {

    @Override
    public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<Integer, User> map = new LinkedHashMap<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            User user = map.get(id);
            if (user == null) {
                user = new User();
            Set<Role> roles = new HashSet<>();
                user.setId(id);
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRegistered(rs.getDate("registered"));
                user.setEnabled(rs.getBoolean("enabled"));
                user.setCaloriesPerDay(rs.getInt("calories_per_day"));
                roles.add(Role.valueOf(rs.getString("role")));
                user.setRoles(roles);
                map.put(id, user);
            } else {
                user.getRoles().add(Role.valueOf(rs.getString("role")));
            }
        }
        return new ArrayList<>(map.values());
    }
}
