package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.storage.MealStorageImpl;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static java.lang.Integer.parseInt;
import static org.slf4j.LoggerFactory.getLogger;

public class MealsServlet extends HttpServlet {
    private final static Logger LOG = getLogger(MealsServlet.class);
    private final static int CALORIES_PER_DAY = 2000;
    private final static DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    MealStorageImpl mealStorage;

    @Override
    public void init(ServletConfig config) {
        mealStorage = new MealStorageImpl();
        mealStorage.save(new Meal(new AtomicLong(0L), LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        mealStorage.save(new Meal(new AtomicLong(0L), LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        mealStorage.save(new Meal(new AtomicLong(0L), LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        mealStorage.save(new Meal(new AtomicLong(0L), LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        mealStorage.save(new Meal(new AtomicLong(0L), LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        mealStorage.save(new Meal(new AtomicLong(0L), LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        mealStorage.save(new Meal(new AtomicLong(0L), LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        final List<MealTo> listMealsTo = MealsUtil.filteredByStreams(mealStorage.getAll(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY);

        LOG.debug("forward to meals");

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");


        String action = request.getParameter("action");
        request.setAttribute("meals", listMealsTo);

        if (action == null) {
            action = "";
        }

        String uuid = request.getParameter("uuid");
        switch (action) {
            case "delete":
                mealStorage.delete((parseInt(uuid)));
                response.sendRedirect("meals");
                return;
            case "edit":
                request.setAttribute("meal", mealStorage.get(parseInt(uuid)));
                break;
            case "save":
                request.setAttribute("meal", MealsUtil.EMPTY);
                break;
            default:
                request.getRequestDispatcher("meals.jsp").forward(request, response);
                return;
        }
        request.getRequestDispatcher("meals.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        Meal meal;
        String date = request.getParameter("date");
        LocalDateTime ldt = LocalDateTime.parse(date, DTF);

        String desc = request.getParameter("desc");
        int cal = parseInt(request.getParameter("cal"));

        if (uuid != null && parseInt(uuid.trim()) != 0) {
            meal = new Meal(new AtomicLong(parseInt(uuid)), ldt, desc, cal);
            mealStorage.update(meal);
        } else {

            mealStorage.save(new Meal(new AtomicLong(0L), ldt, desc, cal));
        }
        response.sendRedirect("meals");
    }
}