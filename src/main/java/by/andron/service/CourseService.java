package by.andron.service;

import by.andron.aspect.annotation.Cacheable;
import by.andron.dto.CourseCreationDto;
import by.andron.exception.ServiceException;
import by.andron.mapper.CourseMapper;
import by.andron.model.Course;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import by.andron.dto.CourseDto;
import by.andron.repository.CourseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Cacheable
@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    private final CourseMapper courseMapper;

    public CourseDto findById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ServiceException("Cannot find course by id in service", HttpStatus.BAD_REQUEST));
        return courseMapper.toDto(course);
    }

    public List<CourseDto> findAll() {
        try {
            return courseMapper.toDto(courseRepository.findAll());
        } catch (Exception e) {
            throw new ServiceException("Cannot find all courses in service", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public CourseDto save(CourseCreationDto courseCreationDto) {
        try {
            Course entity = courseMapper.toEntity(courseCreationDto);
            return courseMapper.toDto(courseRepository.save(entity));
        } catch (Exception e) {
            throw new ServiceException("Cannot save course in service", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public CourseDto update(Long id, CourseCreationDto courseCreationDto) {
        try {
            Course course = courseRepository.findById(id)
                    .orElseThrow(() -> new ServiceException("Cannot find course by id in service", HttpStatus.BAD_REQUEST));
            Course entity = courseMapper.toEntity(courseCreationDto);
            updateCourse(course, entity);
            courseRepository.save(course);
            return courseMapper.toDto(course);
        } catch (Exception e) {
            throw new ServiceException("Cannot update user in service", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            courseRepository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException("Cannot delete this user in service", HttpStatus.BAD_REQUEST);
        }
    }

    private void updateCourse(Course course, Course source){
        course.setName(source.getName());
        course.setTeacher(source.getTeacher());
        course.setUsers(source.getUsers());
        course.setCourseResults(source.getCourseResults());
        course.setStatus(source.getStatus());
        course.setCreateDate(source.getCreateDate());
        course.setExpirationDate(source.getExpirationDate());
    }

}
