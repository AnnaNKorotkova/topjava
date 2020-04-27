package ru.javawebinar.topjava.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ProfileValidator implements ConstraintValidator<ProfileConstrain, String> {
    public void initialize(ProfileConstrain constraint) {
    }

    public boolean isValid(String obj, ConstraintValidatorContext context) {
        return  !(System.getProperty("profile.validation") == null);
    }
}
