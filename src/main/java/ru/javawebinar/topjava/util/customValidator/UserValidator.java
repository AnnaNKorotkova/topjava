package ru.javawebinar.topjava.util.customValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.validation.ConstraintViolation;
import java.util.Set;

@Component
public class UserValidator implements org.springframework.validation.Validator {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private javax.validation.Validator validator;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserTo.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Set<ConstraintViolation<Object>> validates = validator.validate(target);

        for (ConstraintViolation<Object> constraintViolation : validates) {
            String propertyPath = constraintViolation.getPropertyPath().toString();
            String message = constraintViolation.getMessage();
            errors.rejectValue(propertyPath, "", message);
        }

        UserTo user = (UserTo) target;
        boolean isExist = userRepository.getByEmail(user.getEmail()) != null;
        if (isExist) {
            if (user.isNew()) {
                errors.rejectValue("email", "OneTime", "user with this email already exist");
            } else {
                User currentUser = userRepository.get(SecurityUtil.authUserId());
                if (!user.getEmail().equals(currentUser.getEmail())) {
                    errors.rejectValue("email", "OneTime", "user with this email already exist");
                }
            }
        }
    }
}
