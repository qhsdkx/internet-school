package by.andron.controller;

import by.andron.dto.CourseResultCreationDto;
import by.andron.dto.CourseResultDto;
import by.andron.service.CourseResultService;
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
@RequestMapping(value = "/course-results")
@RequiredArgsConstructor
@Tag(name = "Course Result Controller", description = "API for course results")
public class CourseResultController {

    private final CourseResultService service;

    @PreAuthorize("hasAuthority('READ_COURSE_RESULT')")
    @GetMapping("/{id}")
    @Operation(summary = "Find course result by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Everything's OK"),
            @ApiResponse(responseCode = "404", description = "Cannot find course result"),
            @ApiResponse(responseCode = "500", description = "Internal server error in the course results"),
    })
    public ResponseEntity<CourseResultDto> findById(
            @PathVariable("id")
            @Parameter(description = "Course result id", example = "777") Long id
    ) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('READ_COURSE_RESULT')")
    @GetMapping
    @Operation(summary = "Find all course results")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Everything's OK"),
            @ApiResponse(responseCode = "404", description = "Cannot find course results"),
            @ApiResponse(responseCode = "500", description = "Internal server error in the course results"),
    })
    public ResponseEntity<List<CourseResultDto>> findAll(){
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('CREATE_COURSE_RESULT')")
    @PostMapping
    @Operation(summary = "Save course result")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Everything's OK"),
            @ApiResponse(responseCode = "404", description = "Cannot find course results"),
            @ApiResponse(responseCode = "500", description = "Internal server error in the course results"),
    })
    public ResponseEntity<CourseResultDto> save (
            @RequestBody
            @Valid
            @Parameter(description = "Fill up course result", required = true) CourseResultCreationDto courseResultCreationDto
    ) {
        return new ResponseEntity<>(service.save(courseResultCreationDto), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('UPDATE_COURSE_RESULT')")
    @PutMapping(value = "/{id}")
    @Operation(summary = "Update course result by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Everything's OK"),
            @ApiResponse(responseCode = "404", description = "Cannot update course result"),
            @ApiResponse(responseCode = "500", description = "Internal server error in the course results"),
    })
    public ResponseEntity<HttpStatus> update(
            @PathVariable("id")
            @Parameter(description = "Course result id", example = "777") Long id,
            @RequestBody
            @Valid
            @Parameter(description = "Fill up the new course result", required = true) CourseResultCreationDto dto
    ) {
        service.update(id, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('DELETE_COURSE_RESULT')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete course result by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Everything's OK"),
            @ApiResponse(responseCode = "404", description = "Cannot delete course result"),
            @ApiResponse(responseCode = "500", description = "Internal server error in the course results"),
    })
    public ResponseEntity<HttpStatus> delete(
            @PathVariable("id")
            @Parameter(description = "Course result id", example = "777") Long id
    )
    {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
