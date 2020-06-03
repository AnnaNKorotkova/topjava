package ru.javawebinar.topjava.util.customValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.List;
import java.util.stream.Collectors;

//@Component
public class MealValidator  implements org.springframework.validation.Validator {

    @Autowired
    private  MealRepository mealRepository;

    @Override
    public boolean supports(Class<?>    clazz) {
        return Meal.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Meal meal = (Meal) target;
       if (meal.isNew()) {
           List<Meal> list = mealRepository.getAll(SecurityUtil.authUserId())
                   .stream()
                   .filter(x-> x.getDateTime().equals(meal.getDateTime()))
                   .collect(Collectors.toList());
           if (list.size()>0) {
               errors.rejectValue("dateTime", "OneTime","You can't add one more meal at the same time");
           }
       }
    }
}
