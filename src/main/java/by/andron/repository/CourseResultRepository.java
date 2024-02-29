package by.andron.repository;

import by.andron.model.CourseResult;

import java.util.List;

public interface CourseResultRepository {

    CourseResult findById(Long id);

    List<CourseResult> findAll();

    CourseResult save(CourseResult role);

    void update(Long id, CourseResult role);

    void delete(Long id);

}
