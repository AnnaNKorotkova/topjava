package ru.javawebinar.topjava;

public class Profiles {

    public static final String
            JDBC = "jdbc",
            JPA = "jpa",
            DATAJPA = "datajpa";

//    public static final String REPOSITORY_IMPLEMENTATION = JPA;

public static String getActiveRepoProfile() {
    try {
        Class.forName("ru.javawebinar.topjava.repository.datajpa.DataJpaMealRepository");
        Class.forName("ru.javawebinar.topjava.repository.datajpa.DataUserMealRepository");
        return DATAJPA;
    } catch (ClassNotFoundException ex) {
        try {
            Class.forName("ru.javawebinar.topjava.repository.jpa.JpaMealRepository");
            Class.forName("ru.javawebinar.topjava.repository.jpa.JpaUserRepository");
            return JPA;
        } catch (ClassNotFoundException e) {
            try {
                Class.forName("ru.javawebinar.topjava.repository.jdbc.JdbcMealRepository");
                Class.forName("ru.javawebinar.topjava.repository.jdbc.JdbcUserRepository");
                return JDBC;
            } catch (ClassNotFoundException classNotFoundException) {
                throw new IllegalStateException("Bean not found");
            }
        }
    }
}

    public static final String
            POSTGRES_DB = "postgres",
            HSQL_DB = "hsqldb";

    //  Get DB profile depending of DB driver in classpath
    public static String getActiveDbProfile() {
        try {
            Class.forName("org.postgresql.Driver");
            return POSTGRES_DB;
        } catch (ClassNotFoundException ex) {
            try {
                Class.forName("org.hsqldb.jdbcDriver");
                return Profiles.HSQL_DB;
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("Could not find DB driver");
            }
        }
    }
}
