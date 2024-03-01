package by.andron.repository;

import by.andron.model.Course;

import java.util.List;
import java.util.Optional;

public interface CourseRepository {

    Optional<Course> findById(Long id);

    List<Course> findAll(int page, int size);

    Course save(Course role);

    void update(Long id, Course role);

    void delete(Long id);

}
