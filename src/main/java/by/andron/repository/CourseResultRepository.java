package by.andron.repository;

import by.andron.model.CourseResult;

import java.util.List;
import java.util.Optional;

public interface CourseResultRepository {

    Optional<CourseResult> findById(Long id);

    List<CourseResult> findAll(int page, int size);

    CourseResult save(CourseResult role);

    void update(Long id, CourseResult role);

    void delete(Long id);

}
