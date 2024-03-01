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
        try {
            return courseRepository.findAll(page, size).stream()
                    .map(courseMapper::toDto).toList();
        } catch (Exception e) {
            throw new ServiceException("Cannot find all courses in service", HttpStatus.BAD_REQUEST);
        }
    }

    public CourseDto save(CourseCreationDto courseCreationDto) {
        try {
            Course entity = courseMapper.toEntity(courseCreationDto);
            return courseMapper.toDto(courseRepository.save(entity));
        } catch (Exception e) {
            throw new ServiceException("Cannot save course in service", HttpStatus.BAD_REQUEST);
        }
    }

    public void update(Long id, CourseCreationDto courseCreationDto) {
        try {
            courseRepository.update(id, courseMapper.toEntity(courseCreationDto));
        } catch (Exception e) {
            throw new ServiceException("Cannot update user in service", HttpStatus.BAD_REQUEST);
        }
    }

    public void delete(Long id) {
        try {
            courseRepository.delete(id);
        } catch (Exception e) {
            throw new ServiceException("Cannot delete this user in service", HttpStatus.BAD_REQUEST);
        }
    }

}
