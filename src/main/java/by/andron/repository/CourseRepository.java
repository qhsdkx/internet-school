package by.andron.repository;

import by.andron.model.Course;

import java.util.List;

public interface CourseRepository {

    Course findById(Long id);

    List<Course> findAll();

    Course save(Course role);

    void update(Long id, Course role);

    void delete(Long id);

}
