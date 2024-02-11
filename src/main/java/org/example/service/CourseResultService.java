package org.example.service;

import org.example.model.CourseResult;
import org.example.repository.CourseResultRepository;

import java.util.List;

public class CourseResultService {
    private final CourseResultRepository courseResultRepository;

    public CourseResultService(CourseResultRepository courseResultRepository) {
        this.courseResultRepository = courseResultRepository;
    }

    public CourseResult findById(Long id) {
        return courseResultRepository.findById(id);
    }

    public List<CourseResult> findAll() {
        return courseResultRepository.findAll();
    }

    public CourseResult save(CourseResult courseResult) {
        return courseResultRepository.save(courseResult);
    }

    public void update(CourseResult courseResult) {
        courseResultRepository.update(courseResult);
    }

    public void delete(Long id) {
        courseResultRepository.delete(id);
    }
}
