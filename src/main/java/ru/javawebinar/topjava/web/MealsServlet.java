package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.MealsTestData;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.storage.MealStorageImpl;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealsServlet extends HttpServlet {
    private static final Logger log = getLogger(MealsServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        log.debug("forward to meals");
        final int caloriesPerDay = 2000;
        final LocalTime start = LocalTime.MIN;
        final LocalTime end = LocalTime.MAX;
        final List<MealTo> mealsTo = MealsUtil.filteredByStreams(MealsTestData.getList(), start, end, caloriesPerDay);

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        MealStorageImpl mealStorage = new MealStorageImpl();
        mealStorage.setStorage(MealsTestData.getList());


        request.setAttribute("meals", mealsTo);
        request.getRequestDispatcher("meals.jsp").forward(request, response);
    }
}


