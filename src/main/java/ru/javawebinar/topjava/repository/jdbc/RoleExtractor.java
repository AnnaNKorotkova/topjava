package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RoleExtractor implements ResultSetExtractor<Map<String, List<String>>> {

    @Override
    public Map<String, List<String>> extractData(ResultSet rs)
            throws SQLException, DataAccessException {
        Map<String, List<String>> data = new LinkedHashMap<>();
        while (rs.next()) {
            String user = rs.getString("user_id");
            data.putIfAbsent(user, new ArrayList<>());
            String role = rs.getString("role");
            data.get(user).add(role);
        }
        return data;
    }
}
