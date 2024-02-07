package org.example.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    public CourseResult(Long id, Integer score, String feedback, LocalDate endDate) {
        this.id = id;
        this.score = score;
        this.feedback = feedback;
        this.endDate = endDate;
        this.users = new ArrayList<>();
        this.courses = new ArrayList<>();
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
                '}';
    }
}
