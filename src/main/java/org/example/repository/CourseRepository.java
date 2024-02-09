package org.example.repository;

import org.example.model.Course;
import java.util.List;

public interface CourseRepository {
    public Course findById(Long id);
    public List<Course> findAll();
    public Course save(Course role);
    public void update(Course role);
    public void delete(Long id);
}
