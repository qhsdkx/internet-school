package by.andron.controller;

import by.andron.dto.AuthorityCreationDto;
import by.andron.dto.AuthorityDto;
import by.andron.service.AuthorityService;
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
@RequiredArgsConstructor
@RequestMapping("/authorities")
@Tag(name = "Authority controller", description = "API for authority's operations")
public class AuthorityController {

    private final AuthorityService service;

    @PreAuthorize("hasAuthority('READ_AUTHORITY')")
    @GetMapping("/{id}")
    @Operation(summary = "Find authority by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Everything's OK"),
            @ApiResponse(responseCode = "404", description = "Cannot find authority"),
            @ApiResponse(responseCode = "500", description = "Internal server error in the authorities"),
    })
    public ResponseEntity<AuthorityDto> findById(
            @PathVariable("id")
            @Parameter(description = "Authority id", example = "777") Long id
    ) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('READ_AUTHORITY')")
    @GetMapping
    @Operation(summary = "Find all authorities")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Everything's OK"),
            @ApiResponse(responseCode = "404", description = "Cannot find all authorities"),
            @ApiResponse(responseCode = "500", description = "Internal server error in the authorities"),
    })
    public ResponseEntity<List<AuthorityDto>> findAll(){
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('CREATE_AUTHORITY')")
    @PostMapping
    @Operation(summary = "Save authority")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Everything's OK"),
            @ApiResponse(responseCode = "404", description = "Cannot save this authority"),
            @ApiResponse(responseCode = "500", description = "Internal server error in the authorities"),
    })
    public ResponseEntity<AuthorityDto> save(
            @RequestBody
            @Valid
            @Parameter(description = "Fill up the authority", required = true) AuthorityCreationDto dto
    ) {
        return new ResponseEntity<>(service.save(dto), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('UPDATE_AUTHORITY')")
    @PutMapping("/{id}")
    @Operation(summary = "Update authority by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Everything's OK"),
            @ApiResponse(responseCode = "404", description = "Cannot update this authority"),
            @ApiResponse(responseCode = "500", description = "Internal server error in the authorities"),
    })
    public ResponseEntity<HttpStatus> update(
            @PathVariable("id")
            @Parameter(description = "Authority id", example = "777") Long id,
            @RequestBody
            @Valid
            @Parameter(description = "Fill up the new authority", required = true) AuthorityCreationDto dto
    ) {
        service.update(id, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('DELETE_AUTHORITY')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete authority by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Everything's OK"),
            @ApiResponse(responseCode = "404", description = "Cannot delete this authority"),
            @ApiResponse(responseCode = "500", description = "Internal server error in the authorities"),
    })
    public ResponseEntity<HttpStatus> delete(
            @PathVariable("id")
            @Parameter(description = "Suthority id", example = "777") Long id
    ) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
