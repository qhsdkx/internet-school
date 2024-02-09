package org.example.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "course_results")
public class CourseResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer score;
    private String feedback;
    @Column(name = "end_date")
    private LocalDate endDate;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<User> users;
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private List<Course> courses;

    public CourseResult(Long id, Integer score, String feedback, LocalDate endDate, List<User> users, List<Course> courses) {
        this.id = id;
        this.score = score;
        this.feedback = feedback;
        this.endDate = endDate;
        this.users = users;
        this.courses = courses;
    }

    public CourseResult() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "CourseResult{" +
                "id=" + id +
                ", score=" + score +
                ", feedback='" + feedback + '\'' +
                ", endDate=" + endDate +
                ", users=" + users +
                ", courses=" + courses +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseResult that = (CourseResult) o;
        return Objects.equals(id, that.id) && Objects.equals(score, that.score) && Objects.equals(feedback, that.feedback) && Objects.equals(endDate, that.endDate) && Objects.equals(users, that.users) && Objects.equals(courses, that.courses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, score, feedback, endDate, users, courses);
    }
}
