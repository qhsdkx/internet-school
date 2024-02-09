package org.example.service;

import org.example.model.CourseResult;
import org.example.repository.CourseResultRepository;

import java.util.List;

public class CourseResultService {
    private final CourseResultRepository courseResultRepository;

    public CourseResultService(CourseResultRepository courseResultRepository) {
        this.courseResultRepository = courseResultRepository;
    }

    public CourseResult findCourseResult(Long id) {
        return courseResultRepository.findById(id);
    }

    public List<CourseResult> findAllCourseResults() {
        return courseResultRepository.findAll();
    }

    public CourseResult saveCourseResult(CourseResult courseResult) {
        return courseResultRepository.save(courseResult);
    }

    public void updateCourseResult(CourseResult courseResult) {
        courseResultRepository.update(courseResult);
    }

    public void deleteCourseResult(Long id) {
        courseResultRepository.delete(id);
    }
}
