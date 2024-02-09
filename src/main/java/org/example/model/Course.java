package org.example.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "create_date")
    private LocalDate createDate;
    @Column(name = "expiration_date")
    private LocalDate expirationDate;
    private String status;
    @ManyToOne(fetch = FetchType.LAZY)
    private User teacher;
    @ManyToMany
    @JoinTable(
            name = "student_course_links",
            joinColumns =  {@JoinColumn(name = "course_id")},
            inverseJoinColumns =  {@JoinColumn(name = "user_id")}
    )
    private Set<User> users;

    public Course(Long id, String name, LocalDate createDate, LocalDate expirationDate, String status) {
        this.id = id;
        this.name = name;
        this.createDate = createDate;
        this.expirationDate = expirationDate;
        this.status = status;
        this.teacher = new User();
        this.users = new HashSet<>();
    }

    public Course() {
    }

    public Long getId() {
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

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createDate=" + createDate +
                ", expirationDate=" + expirationDate +
                ", status='" + status + '\'' +
                ", teacher=" + teacher +
                ", users=" + users +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(id, course.id) && Objects.equals(name, course.name) && Objects.equals(createDate, course.createDate) && Objects.equals(expirationDate, course.expirationDate) && Objects.equals(status, course.status) && Objects.equals(teacher, course.teacher) && Objects.equals(users, course.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, createDate, expirationDate, status, teacher, users);
    }
}
