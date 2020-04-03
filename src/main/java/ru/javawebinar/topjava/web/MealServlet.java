package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    MealRestController mealRestController;
    ConfigurableApplicationContext appCtx;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        mealRestController = appCtx.getBean(MealRestController.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        String action = request.getParameter("action");
        switch (action) {
            case "add":
                Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                        LocalDateTime.parse(request.getParameter("dateTime")),
                        request.getParameter("description"),
                        Integer.parseInt(request.getParameter("calories")));
                log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
                if (meal.isNew()) {
                    mealRestController.create(meal);
                } else {
                    mealRestController.update(meal);
                }
                break;
            case "filter":
                String startTime = request.getParameter("startTime");
                LocalTime slt =
                        takeDateTime(startTime, x -> LocalTime.parse(startTime));

                String endTime = request.getParameter("endTime");
                LocalTime elt = takeDateTime(endTime, x -> LocalTime.parse(endTime));

                String startDate = request.getParameter("startDate");
                LocalDate sld = takeDateTime(startDate, x -> LocalDate.parse(startDate));

                String endDate = request.getParameter("endDate");
                LocalDate eld = takeDateTime(endDate, x -> LocalDate.parse(endDate));

                request.setAttribute("meals", mealRestController.getAllFiltered(slt, elt, sld, eld));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                mealRestController.delete(id);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        mealRestController.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "all":
            default:

                log.info("getAll");
                request.setAttribute("meals", mealRestController.getAll());
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        appCtx.close();
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }

    private interface DateTimeParse<T> {
        T parse(String param);
    }

    private <T> T takeDateTime(String param, DateTimeParse<T> takeDateTimeInt) {
        if (param != null && param.trim().length() > 4) {
            return takeDateTimeInt.parse(param);
        } else {
            return null;
        }
    }
}
