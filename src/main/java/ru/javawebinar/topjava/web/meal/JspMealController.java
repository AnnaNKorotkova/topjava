package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.web.SecurityUtil;
import ru.javawebinar.topjava.web.meal.AbstractMealController;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.http.HttpServletRequest;

import java.time.LocalDate;
import java.time.LocalTime;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
public class JspMealController extends AbstractMealController {
    @Autowired
    private MealService service;

    public JspMealController(MealService service) {
        super(service);
    }


    @GetMapping("/meals")
    public String getMeals(Model model) {
        model.addAttribute("meals", service.getAll(SecurityUtil.authUserId()));
        return "meals";
    }

    @GetMapping("/meals/filter")
    public String getMealsFiltered(Model model, HttpServletRequest request) {
        model.addAttribute("meals", service.getAll(SecurityUtil.authUserId()));
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        request.setAttribute("meals", getBetween(startDate, startTime, endDate, endTime));
        return "redirect:meals";
    }

    @GetMapping("/meals/create")
    public String create() {
        return "redirect:mealForm";

    }    @GetMapping("/meals/update")
    public String update(Model model, HttpServletRequest request) {
        String mealId = request.getParameter("id");
        model.addAttribute(mealId);
        return "redirect:mealForm";
    }

    @GetMapping("/meals/delete")
    public String delete(Model model, HttpServletRequest request) {
        String mealId = request.getParameter("id");
        service.delete(Integer.parseInt(mealId), SecurityUtil.authUserId());
        return "redirect:meals";
    }

    @PostMapping("/meals")
    public String setUser(HttpServletRequest request) {
        int userId = Integer.parseInt(request.getParameter("userId"));
        SecurityUtil.setAuthUserId(userId);
        return "redirect:meals";
    }
}
