package by.andron.controller;

import by.andron.dto.CourseResultCreationDto;
import by.andron.dto.CourseResultDto;
import by.andron.service.CourseResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/course-results")
@RequiredArgsConstructor
public class CourseResultController {

    private final CourseResultService service;

    @PreAuthorize("hasAuthority('READ_COURSE_RESULT')")
    @GetMapping("/{id}")
    public ResponseEntity<CourseResultDto> findById(@PathVariable("id") Long id){
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('READ_COURSE_RESULT')")
    @GetMapping
    public ResponseEntity<List<CourseResultDto>> findAll(){
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('CREATE_COURSE_RESULT')")
    @PostMapping
    public ResponseEntity<CourseResultDto> save (@RequestBody CourseResultCreationDto courseResultCreationDto){
        return new ResponseEntity<>(service.save(courseResultCreationDto), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('UPDATE_COURSE_RESULT')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable("id") Long id, @RequestBody CourseResultCreationDto dto){
        service.update(id, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('DELETE_COURSE_RESULT')")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id){
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
