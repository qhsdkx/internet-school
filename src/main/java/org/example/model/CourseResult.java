package org.example.model;

import java.time.LocalDate;

public class CourseResult {

    Long id;
    Integer score;
    String feedback;
    LocalDate endDate;
    Long userId;

    public CourseResult(Long id, Integer score, String feedback, LocalDate endDate, Long userId) {
        this.id = id;
        this.score = score;
        this.feedback = feedback;
        this.endDate = endDate;
        this.userId = userId;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "CourseResult{" +
                "id=" + id +
                ", score=" + score +
                ", feedback='" + feedback + '\'' +
                ", endDate=" + endDate +
                ", userId=" + userId +
                '}';
    }
}
