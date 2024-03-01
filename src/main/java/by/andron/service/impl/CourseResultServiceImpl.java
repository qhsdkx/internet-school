package by.andron.service.impl;

import by.andron.dto.CourseResultCreationDto;
import by.andron.exception.ServiceException;
import by.andron.mapper.CourseResultMapper;
import by.andron.service.CourseResultService;
import lombok.RequiredArgsConstructor;
import by.andron.dto.CourseResultDto;
import by.andron.model.CourseResult;
import by.andron.repository.CourseResultRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseResultServiceImpl implements CourseResultService {

    private final CourseResultRepository courseResultRepository;

    private final CourseResultMapper courseResultMapper;

    public CourseResultDto findById(Long id) {
        CourseResult courseResult = courseResultRepository.findById(id).orElseThrow(() -> new ServiceException("Cannot find course by id in service", HttpStatus.BAD_REQUEST));
        return courseResultMapper.toDto(courseResult);
    }

    public List<CourseResultDto> findAll(int page, int size) {
        try {
            return courseResultRepository.findAll(page, size).stream()
                    .map(courseResultMapper::toDto).toList();
        } catch (Exception e) {
            throw new ServiceException("Cannot find all course results in service", HttpStatus.BAD_REQUEST);
        }
    }

    public CourseResultDto save(CourseResultCreationDto courseResultCreationDto) {
        try {
            CourseResult entity = courseResultMapper.toEntity(courseResultCreationDto);
            return courseResultMapper.toDto(courseResultRepository.save(entity));
        } catch (Exception e) {
            throw new ServiceException("Cannot save this course result in service", HttpStatus.BAD_REQUEST);
        }
    }

    public void update(Long id, CourseResultCreationDto courseResultCreationDto) {
        try {
            courseResultRepository.update(id, courseResultMapper.toEntity(courseResultCreationDto));
        } catch (Exception e) {
            throw new ServiceException("Cannot update this course result in service", HttpStatus.BAD_REQUEST);
        }
    }

    public void delete(Long id) {
        try {
            courseResultRepository.delete(id);
        } catch (Exception e) {
            throw new ServiceException("Cannot delete this course result in service", HttpStatus.BAD_REQUEST);
        }
    }

}
