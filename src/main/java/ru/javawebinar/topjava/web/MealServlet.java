package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.ProfileRestController;

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

    ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
    MealRestController mealRestController = appCtx.getBean(MealRestController.class);
    ProfileRestController profileRestController = appCtx.getBean(ProfileRestController.class);

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
                mealRestController.create(SecurityUtil.authUserId(), meal);
                break;
            case "filter":
                LocalTime slt;
                String startTime = request.getParameter("startTime");
                if (startTime != null && startTime.trim().length() > 4) {
                    slt = LocalTime.parse(startTime);
                } else {
                    slt = LocalTime.MIN;
                }

                LocalTime elt;
                String endTime = request.getParameter("endTime");
                if (endTime != null && endTime.trim().length() > 4) {
                    elt = LocalTime.parse(endTime);
                } else {
                    elt = LocalTime.MAX;
                }

                LocalDate sld;
                String startDate = request.getParameter("startDate");
                if (startDate != null && startDate.trim().length() > 4) {
                    sld = LocalDate.parse(startDate);
                } else {
                    sld = LocalDate.MIN;
                }

                LocalDate eld;
                String endDate = request.getParameter("endDate");
                if (endDate != null && endDate.trim().length() > 4) {
                    eld = LocalDate.parse(endDate);
                } else {
                    eld = LocalDate.MAX;
                }
                request.setAttribute("meals", mealRestController.getAllByTime(slt, elt, sld, eld, profileRestController.get(SecurityUtil.authUserId()))); // .getAll(profileRestController.get(SecurityUtil.authUserId())));
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
                mealRestController.delete(1, id);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        mealRestController.get(SecurityUtil.authUserId(), getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "all":
            default:

                log.info("getAll");
                request.setAttribute("meals", mealRestController.getAll(profileRestController.get(SecurityUtil.authUserId())));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
