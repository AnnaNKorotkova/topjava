package ru.javawebinar.topjava.model;

import org.hibernate.annotations.SelectBeforeUpdate;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@NamedQueries({
        @NamedQuery(name = Meal.UPDATE, query = "UPDATE Meal m SET m.description=:description, m.calories=:calories, " +
                "m.dateTime=:date_time WHERE m.id=:id AND m.user.id=:user_id"),
        @NamedQuery(name = Meal.DELETE, query = "DELETE FROM Meal m WHERE m.id=:id and m.user.id=:userId"),
        @NamedQuery(name = Meal.GET, query = "SELECT m FROM Meal m WHERE m.id=:id AND m.user.id=:userId"),
        @NamedQuery(name = Meal.GET_ALL_SORTED, query = "SELECT m FROM Meal m WHERE m.user.id=:userId ORDER BY m.dateTime DESC"),
        @NamedQuery(name = Meal.GET_BETWEEN_HALF_OPEN, query = "SELECT m FROM Meal m WHERE m.user.id=:userId " +
                "and m.dateTime >=:startDate and m.dateTime <:endDate ORDER BY m.dateTime DESC")
})
@Entity
//@SelectBeforeUpdate
@Table(name = "meals", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "date_time"}, name = "meals_user_id_date_time_uindex")})
public class Meal extends AbstractBaseEntity {

    public static final String UPDATE = "Meal.update";
    public static final String DELETE = "Meal.delete";
    public static final String GET = "Meal.get";
    public static final String GET_ALL_SORTED = "Meal.getAllSorted";
    public static final String GET_BETWEEN_HALF_OPEN = "Meal.getBetweenHalfOpen";

    @Column(name = "date_time", nullable = false)
    @NotNull
    private LocalDateTime dateTime;

    @Column(name = "description", nullable = false)
    @NotNull
    @NotBlank
    private String description;

    @Column(name = "calories", nullable = false)
    @Min(10)
    @Max(5000)
    private int calories;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Meal() {
    }

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories, User user) {
        this(id, dateTime, description, calories);
        this.user = user;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
