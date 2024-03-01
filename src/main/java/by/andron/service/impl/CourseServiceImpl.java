package by.andron.service.impl;

import by.andron.dto.CourseCreationDto;
import by.andron.exception.ServiceException;
import by.andron.mapper.CourseMapper;
import by.andron.model.Course;
import by.andron.service.CourseService;
import lombok.RequiredArgsConstructor;
import by.andron.dto.CourseDto;
import by.andron.repository.CourseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    private final CourseMapper courseMapper;

    public CourseDto findById(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new ServiceException("Cannot find course by id in service", HttpStatus.BAD_REQUEST));
        return courseMapper.toDto(course);
    }

    public List<CourseDto> findAll(int page, int size) {
        return courseRepository.findAll(page, size).stream()
                .map(courseMapper::toDto).toList();
    }

    public CourseDto save(CourseCreationDto courseCreationDto) {
        Course entity = courseMapper.toEntity(courseCreationDto);
        return courseMapper.toDto(courseRepository.save(entity));
    }

    public void update(Long id, CourseCreationDto courseCreationDto) {
        courseRepository.update(id, courseMapper.toEntity(courseCreationDto));
    }

    public void delete(Long id) {
        courseRepository.delete(id);
    }

}
