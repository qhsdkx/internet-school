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
        return courseResultRepository.findAll(page, size).stream()
                .map(courseResultMapper::toDto).toList();
    }

    public CourseResultDto save(CourseResultCreationDto courseResultCreationDto) {
        CourseResult entity = courseResultMapper.toEntity(courseResultCreationDto);
        return courseResultMapper.toDto(courseResultRepository.save(entity));
    }

    public void update(Long id, CourseResultCreationDto courseResultCreationDto) {
        courseResultRepository.update(id, courseResultMapper.toEntity(courseResultCreationDto));
    }

    public void delete(Long id) {
        courseResultRepository.delete(id);
    }

}
