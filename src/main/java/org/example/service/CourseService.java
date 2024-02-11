package org.example.service;

import org.example.model.Course;
import org.example.repository.CourseRepository;

import java.util.List;

public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course findById(Long id) {
        return courseRepository.findById(id);
    }

    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    public Course save(Course course) {
        return courseRepository.save(course);
    }

    public void update(Course course) {
        courseRepository.update(course);
    }

    public void delete(Long id) {
        courseRepository.delete(id);
    }
}
