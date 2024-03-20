package by.andron.controller;

import by.andron.dto.CourseCreationDto;
import by.andron.dto.CourseDto;
import by.andron.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@Tag(name = "Course controller", description = "API for course's operations")
public class CourseController {

    private final CourseService service;

    @PreAuthorize("hasAuthority('READ_COURSE')")
    @GetMapping("/{id}")
    @Operation(summary = "Find course by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Everything's OK"),
            @ApiResponse(responseCode = "404", description = "Cannot find course"),
            @ApiResponse(responseCode = "500", description = "Internal server error in the courses"),
    })
    public ResponseEntity<CourseDto> findById(
            @PathVariable("id")
            @Parameter(description = "Course id", example = "777") Long id
    ) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('READ_COURSE')")
    @GetMapping
    @Operation(summary = "Find all courses by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Everything's OK"),
            @ApiResponse(responseCode = "404", description = "Cannot find courses"),
            @ApiResponse(responseCode = "500", description = "Internal server error in the courses"),
    })
    public ResponseEntity<List<CourseDto>> findAll(){
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('CREATE_COURSE') and #courseCreationDto.teacherId == @userService.findUserByLogin(authentication.name).id")
    @PostMapping
    @Operation(summary = "Save course")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Everything's OK"),
            @ApiResponse(responseCode = "404", description = "Cannot save course"),
            @ApiResponse(responseCode = "500", description = "Internal server error in the courses"),
    })
    public ResponseEntity<CourseDto> save (
            @RequestBody
            @Valid
            @Parameter(description = "Fill up course", required = true) CourseCreationDto courseCreationDto
    ) {
        return new ResponseEntity<>(service.save(courseCreationDto), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('UPDATE_COURSE')")
    @PutMapping(value = "/{id}")
    @Operation(summary = "Update course by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Everything's OK"),
            @ApiResponse(responseCode = "404", description = "Cannot update course"),
            @ApiResponse(responseCode = "500", description = "Internal server error in the courses"),
    })
    public ResponseEntity<HttpStatus> update(
            @PathVariable("id")
            @Parameter(description = "Course id", example = "777") Long id,
            @RequestBody
            @Valid
            @Parameter(description = "Fill up fields in new course", required = true) CourseCreationDto dto
    ) {
        service.update(id, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('DELETE_COURSE')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete course")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Everything's OK"),
            @ApiResponse(responseCode = "404", description = "Cannot delete course"),
            @ApiResponse(responseCode = "500", description = "Internal server error in the courses"),
    })
    public ResponseEntity<HttpStatus> delete(
            @PathVariable("id")
            @Parameter(description = "Course id", example = "777") Long id
    ) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
