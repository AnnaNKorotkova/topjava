package ru.javawebinar.topjava.util.customValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.validation.ConstraintViolation;
import java.util.Set;

@Component
public class MealValidator implements org.springframework.validation.Validator {

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private javax.validation.Validator validator;

    @Override
    public boolean supports(Class<?> clazz) {
        return Meal.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Set<ConstraintViolation<Object>> validates = validator.validate(target);

        for (ConstraintViolation<Object> constraintViolation : validates) {
            String propertyPath = constraintViolation.getPropertyPath().toString();
            String message = constraintViolation.getMessage();
            errors.rejectValue(propertyPath, "", message);
        }

        Meal meal = (Meal) target;
        if (meal.getDateTime() != null) {
            final Meal mealByUserIdAndDateTime = mealRepository.getMealByUserIdAndDateTime(SecurityUtil.authUserId(), meal.getDateTime());
            boolean isExist = mealByUserIdAndDateTime != null;
            if (isExist) {
                if (meal.isNew()) {
                    errors.rejectValue("dateTime", "OneTime", "you can't add one more meal at the same time");
                } else {
                    if (!meal.getId().equals(mealByUserIdAndDateTime.getId())) {
                        errors.rejectValue("dateTime", "OneTime", "you can't add one more meal at the same time");
                    }
                }
            }
        }
    }
}

