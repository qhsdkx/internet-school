package by.andron.service;

import by.andron.dto.CourseResultCreationDto;
import by.andron.dto.CourseResultDto;

import java.util.List;

public interface CourseResultService {
    CourseResultDto findById(Long id);
    List<CourseResultDto> findAll();
    CourseResultDto save(CourseResultCreationDto courseResultCreationDto);
    void update(Long id, CourseResultCreationDto courseResultCreationDto);
    void delete(Long id);
}
