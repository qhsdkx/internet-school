package org.example.model;

import jakarta.persistence.*;

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
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "course_id")}
    )
    private Set<Course> courses;
    @ManyToMany
    @JoinTable(
            name = "user_role_links",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private Set<Role> roles;

    public User(Long id, String name, String surname, String login, String password, LocalDate birthDay) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.birthDay = birthDay;
        this.courseResults = new ArrayList<>();
        this.teacherCourses = new ArrayList<>();
        this.roles = new HashSet<>();
        this.courses = new HashSet<>();
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

    public List<Course> getTeacherCourses() {
        return teacherCourses;
    }

    public void setTeacherCourses(List<Course> teacherCourses) {
        this.teacherCourses = teacherCourses;
    }

    public List<CourseResult> getCourseResults() {
        return courseResults;
    }

    public void setCourseResults(List<CourseResult> courseResults) {
        this.courseResults = courseResults;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
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
                ", teacherCourses=" + teacherCourses +
                ", courseResults=" + courseResults +
                ", courses=" + courses +
                ", roles=" + roles +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(surname, user.surname) && Objects.equals(login, user.login) && Objects.equals(password, user.password) && Objects.equals(birthDay, user.birthDay) && Objects.equals(teacherCourses, user.teacherCourses) && Objects.equals(courseResults, user.courseResults) && Objects.equals(courses, user.courses) && Objects.equals(roles, user.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, login, password, birthDay, teacherCourses, courseResults, courses, roles);
    }
}
