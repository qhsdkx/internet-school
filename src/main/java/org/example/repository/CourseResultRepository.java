package org.example.repository;

import org.example.model.CourseResult;

import java.util.List;

public interface CourseResultRepository {
    public CourseResult findById(Long id);
    public List<CourseResult> findAll();
    public CourseResult save(CourseResult role);
    public void update(CourseResult role);
    public void delete(Long id);
}
