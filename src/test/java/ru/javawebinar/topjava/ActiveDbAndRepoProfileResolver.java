package ru.javawebinar.topjava;

import org.springframework.test.context.ActiveProfilesResolver;

import static ru.javawebinar.topjava.Profiles.*;

//http://stackoverflow.com/questions/23871255/spring-profiles-simple-example-of-activeprofilesresolver
public class ActiveDbAndRepoProfileResolver implements ActiveProfilesResolver {

    @Override
    public String[] resolve(Class<?> aClass) {
        String repo = Profiles.getActiveRepoProfile();
        String db = Profiles.getActiveDbProfile();
         return new String[]{db, repo};
    }
}