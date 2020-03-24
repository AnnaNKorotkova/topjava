package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.MealsTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.storage.MealStorageImpl;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static java.lang.Integer.parseInt;
import static org.slf4j.LoggerFactory.getLogger;

public class MealsServlet extends HttpServlet {
    private static final Logger log = getLogger(MealsServlet.class);
    final int caloriesPerDay = 2000;
    final LocalTime start = LocalTime.MIN;
    final LocalTime end = LocalTime.MAX;
    MealStorageImpl mealStorage = new MealStorageImpl();

    {
        mealStorage.setStorage(MealsTestData.getList());
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        final List<MealTo> listMealsTo = MealsUtil.filteredByStreams(mealStorage.getAll(), start, end, caloriesPerDay);

        log.debug("forward to meals");

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("meals", listMealsTo);
            request.getRequestDispatcher("meals.jsp").forward(request, response);
            return;
        }

        String uuid = request.getParameter("uuid");
        switch (action) {
            case "delete":
                mealStorage.delete(uuid);
                response.sendRedirect("meals");
                break;
            case "edit":
                request.setAttribute("mealSaveEdit", true);
                request.setAttribute("meals", listMealsTo);
                request.setAttribute("meal", mealStorage.get(uuid));
                request.getRequestDispatcher("meals.jsp").forward(request, response);
                return;
            case "save":
                request.setAttribute("mealSaveEdit", true);
                request.setAttribute("meals", listMealsTo);
                request.setAttribute("meal", Meal.EMPTY);
                request.getRequestDispatcher("meals.jsp").forward(request, response);
                return;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        Meal meal;
        String date = request.getParameter("date");
        LocalDateTime ldt = LocalDateTime.of(
                parseInt(date.substring(6, 10)),
                parseInt(date.substring(3, 5)),
                parseInt(date.substring(0, 2)),
                parseInt(date.substring(11, 13)),
                parseInt(date.substring(14))
        );
        String desc = request.getParameter("desc");
        int cal = parseInt(request.getParameter("cal"));

        if (uuid != null && uuid.trim().length() != 0) {
            //           meal = mealStorage.get(uuid);

            meal = new Meal(uuid, ldt, desc, cal);
            mealStorage.update(meal);
        } else {

            meal = new Meal(ldt, desc, cal);
            mealStorage.save(meal);
        }
        response.sendRedirect("meals");
    }
}


