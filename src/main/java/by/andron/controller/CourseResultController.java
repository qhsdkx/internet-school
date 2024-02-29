package by.andron.controller;

import by.andron.dto.CourseResultCreationDto;
import by.andron.dto.CourseResultDto;
import by.andron.service.CourseResultService;
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
@RequestMapping("/courseresults")
@RequiredArgsConstructor
public class CourseResultController {

    private final CourseResultService service;

    @GetMapping("/{id}")
    public CourseResultDto findById(@PathVariable("id") Long id){
        return service.findById(id);
    }

    @GetMapping
    public List<CourseResultDto> findAll(){
        return service.findAll();
    }

    @PostMapping
    public CourseResultDto save (CourseResultCreationDto courseResultCreationDto){
        return service.save(courseResultCreationDto);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable("id") Long id, CourseResultCreationDto dto){
        service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id){
        service.delete(id);
    }

}
