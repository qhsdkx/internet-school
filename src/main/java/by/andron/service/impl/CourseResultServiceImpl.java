package by.andron.service.impl;

import by.andron.dto.CourseResultCreationDto;
import by.andron.mapper.CourseResultMapper;
import by.andron.service.CourseResultService;
import lombok.RequiredArgsConstructor;
import by.andron.dto.CourseResultDto;
import by.andron.model.CourseResult;
import by.andron.repository.CourseResultRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseResultServiceImpl implements CourseResultService {

    private final CourseResultRepository courseResultRepository;

    private final CourseResultMapper courseResultMapper;

    public CourseResultDto findById(Long id) {
        return courseResultMapper.toDto(courseResultRepository.findById(id));
    }

    public List<CourseResultDto> findAll() {
        return courseResultRepository.findAll().stream()
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
