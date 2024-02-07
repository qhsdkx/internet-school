package org.example.model;

import jakarta.persistence.*;
import org.hibernate.dialect.function.array.H2ArrayContainsFunction;

import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private String login;
    private String password;
    @Column(name = "birth_day")
    private LocalDate birthDay;
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private List<Course> teacherCourses;
    @OneToMany(mappedBy = "course_result", fetch = FetchType.LAZY)
    private List<CourseResult> courseResults;
    @ManyToMany
    @JoinTable(
            name = "student_course_links",
            joinColumns =  {@JoinColumn(name = "user_id")},
            inverseJoinColumns =  {@JoinColumn(name = "course_id")}
    )
    Set<Course> courses = new HashSet<>();
    @ManyToMany
    @JoinTable(
            name = "user_role_links",
            joinColumns =  {@JoinColumn(name = "user_id")},
            inverseJoinColumns =  {@JoinColumn(name = "role_id")}
    )
    Set<Role> Roles = new HashSet<>();
    public User(Long id, String name, String surname, String login, String password, LocalDate birthDay) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.birthDay = birthDay;
        this.courseResults = new ArrayList<>();
        this.teacherCourses = new ArrayList<>();
    }

    public User() {
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", birthDay=" + birthDay +
                '}';
    }
}
