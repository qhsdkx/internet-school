package by.andron.controller;

import by.andron.dto.CourseCreationDto;
import by.andron.dto.CourseDto;
import by.andron.dto.CourseResultCreationDto;
import by.andron.dto.CourseResultDto;
import by.andron.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService service;

    @GetMapping("/{id}")
    public CourseDto findById(@PathVariable("id") Long id){
        return service.findById(id);
    }

    @GetMapping
    public List<CourseDto> findAll(){
        return service.findAll();
    }

    @PostMapping
    public CourseDto save (CourseCreationDto courseCreationDto){
        return service.save(courseCreationDto);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable("id") Long id, CourseCreationDto dto){
        service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id){
        service.delete(id);
    }

}
