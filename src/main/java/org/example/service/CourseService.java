package org.example.service;

import org.example.model.Course;
import org.example.repository.CourseRepository;

import java.util.List;

public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course findCourse(Long id) {
        return courseRepository.findById(id);
    }

    public List<Course> findAllCourses() {
        return courseRepository.findAll();
    }

    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    public void updateCourse(Course course) {
        courseRepository.update(course);
    }

    public void deleteCourse(Long id) {
        courseRepository.delete(id);
    }
}
