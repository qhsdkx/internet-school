package org.example.model;

import java.time.LocalDate;

public class Course {
    private Long id;
    private String name;
    private LocalDate createDate;
    private LocalDate expirationDate;
    private String status;
    private Long teacherId;

    public Course(Long id, String name, LocalDate createDate, LocalDate expirationDate, String status, Long teacherId) {
        this.id = id;
        this.name = name;
        this.createDate = createDate;
        this.expirationDate = expirationDate;
        this.status = status;
        this.teacherId = teacherId;
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

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createDate=" + createDate +
                ", expirationDate=" + expirationDate +
                ", status='" + status + '\'' +
                ", teacherId=" + teacherId +
                '}';
    }

}
