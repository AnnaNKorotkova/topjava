package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.jdbc.core.RowMapper;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class UserRowMapper implements RowMapper<List<User>> {
    @Override
    public List<User> mapRow(ResultSet rs, int rowNum) throws SQLException {
        List<User> users = new ArrayList<>();
            boolean hasNext = false;
        do {
            List<Role> roles = new ArrayList<>();
            String email = rs.getString("email");
            String nextEmail = "";
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setEmail(email);
            user.setPassword(rs.getString("password"));
            user.setRegistered(rs.getDate("registered"));
            user.setEnabled(rs.getBoolean("enabled"));
            user.setCaloriesPerDay(rs.getInt("calories_per_day"));
            do {
                roles.add(Role.valueOf(rs.getString("role")));
                hasNext = !rs.isLast();
                if (hasNext) {
                    rs.next();
                    nextEmail = rs.getString("email");
                } else {
                    nextEmail = "";
                }
            } while (email.equals(nextEmail));
            user.setRoles(new HashSet<Role>(roles));
            users.add(user);
        } while (hasNext);
        return users;
    }
}
