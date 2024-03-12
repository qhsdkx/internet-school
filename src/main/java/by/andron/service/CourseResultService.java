package by.andron.service;

import by.andron.dto.CourseResultCreationDto;
import by.andron.exception.ServiceException;
import by.andron.mapper.CourseResultMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import by.andron.dto.CourseResultDto;
import by.andron.model.CourseResult;
import by.andron.repository.CourseResultRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseResultService {

    private final CourseResultRepository courseResultRepository;

    private final CourseResultMapper courseResultMapper;

    public CourseResultDto findById(Long id) {
        CourseResult courseResult = courseResultRepository.findById(id)
                .orElseThrow(() -> new ServiceException("Cannot find course by id in service", HttpStatus.BAD_REQUEST));
        return courseResultMapper.toDto(courseResult);
    }

    public List<CourseResultDto> findAll() {
        try {
            return courseResultMapper.toDto(courseResultRepository.findAll());
        } catch (Exception e) {
            throw new ServiceException("Cannot find all course results in service", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public CourseResultDto save(CourseResultCreationDto courseResultCreationDto) {
        try {
            CourseResult entity = courseResultMapper.toEntity(courseResultCreationDto);
            return courseResultMapper.toDto(courseResultRepository.save(entity));
        } catch (Exception e) {
            throw new ServiceException("Cannot save this course result in service", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public void update(Long id, CourseResultCreationDto courseResultCreationDto) {
        try {
            CourseResult courseResult = courseResultRepository.findById(id)
                    .orElseThrow(() -> new ServiceException("Cannot find course by id in service", HttpStatus.BAD_REQUEST));
            CourseResult entity = courseResultMapper.toEntity(courseResultCreationDto);
            updateCourseResults(courseResult, entity);
            courseResultRepository.save(courseResult);
        } catch (Exception e) {
            throw new ServiceException("Cannot update this course result in service", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            courseResultRepository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException("Cannot delete this course result in service", HttpStatus.BAD_REQUEST);
        }
    }

    private void updateCourseResults(CourseResult courseResult, CourseResult source){
        courseResult.setId(source.getId());
        courseResult.setCourse(source.getCourse());
        courseResult.setUser(source.getUser());
        courseResult.setScore(source.getScore());
        courseResult.setFeedback(source.getFeedback());
        courseResult.setEndDate(source.getEndDate());
    }

}
