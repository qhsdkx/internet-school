package by.andron.controller;

import by.andron.dto.CourseCreationDto;
import by.andron.dto.CourseDto;
import by.andron.service.CourseService;
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
@RequestMapping(value = "/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService service;

    @PreAuthorize("hasAuthority('READ_COURSE')")
    @GetMapping("/{id}")
    public ResponseEntity<CourseDto> findById(@PathVariable("id") Long id){
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('READ_COURSE')")
    @GetMapping
    public ResponseEntity<List<CourseDto>> findAll(){
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('CREATE_COURSE')")
    @PostMapping
    public ResponseEntity<CourseDto> save (@RequestBody CourseCreationDto courseCreationDto){
        return new ResponseEntity<>(service.save(courseCreationDto), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('UPDATE_COURSE')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable("id") Long id, @RequestBody CourseCreationDto dto){
        service.update(id, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('DELETE_COURSE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id){
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
