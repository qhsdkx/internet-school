package by.andron.service;

import by.andron.dto.CourseCreationDto;
import by.andron.dto.CourseDto;

import java.util.List;

public interface CourseService {

    CourseDto findById(Long id);
    List<CourseDto> findAll(int page, int size);
    CourseDto save(CourseCreationDto courseCreationDto);
    void update(Long id, CourseCreationDto courseCreationDto);
    void delete(Long id);
}
