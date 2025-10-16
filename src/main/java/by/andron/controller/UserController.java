package by.andron.controller;

import by.andron.dto.UserCreationDto;
import by.andron.dto.UserDto;
import by.andron.service.UserService;
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
import org.springframework.security.core.parameters.P;
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
@RequestMapping(value = "/users")
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "API for users")
public class UserController {

    private final UserService service;

    @PreAuthorize("hasAuthority('READ_USER')")
    @GetMapping("/{id}")
    @Operation(summary = "Find user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Everything's OK"),
            @ApiResponse(responseCode = "404", description = "Cannot find user"),
            @ApiResponse(responseCode = "500", description = "Internal server error in the users"),
    })
    public ResponseEntity<UserDto> findById(
            @PathVariable("id")
            @Parameter(description = "User id", example = "777") Long id
    ) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('READ_USER')")
    @GetMapping
    @Operation(summary = "Find all users by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Everything's OK"),
            @ApiResponse(responseCode = "404", description = "Cannot find all users"),
            @ApiResponse(responseCode = "500", description = "Internal server error in the users"),
    })
    public ResponseEntity<List<UserDto>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('CREATE_USER')")
    @PostMapping
    @Operation(summary = "Save user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Everything's OK"),
            @ApiResponse(responseCode = "404", description = "Cannot find user"),
            @ApiResponse(responseCode = "500", description = "Internal server error in the users"),
    })
    public ResponseEntity<UserDto> save(
            @RequestBody
            @Valid
            @Parameter(description = "Fill up user", required = true) UserCreationDto userCreationDto
    ) {
        return new ResponseEntity<>(service.save(userCreationDto), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('UPDATE_USER')")
    @PutMapping(value = "/{id}")
    @Operation(summary = "Update user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Everything's OK"),
            @ApiResponse(responseCode = "404", description = "Cannot update user"),
            @ApiResponse(responseCode = "500", description = "Internal server error in the users"),
    })
    public ResponseEntity<HttpStatus> update(
            @PathVariable("id")
            @Parameter(description = "User id", example = "777") Long id,
            @RequestBody
            @Valid
            @Parameter(description = "Fill up the new user", required = true) UserCreationDto userCreationDto
    ) {
        service.update(id, userCreationDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('DELETE_USER')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Everything's OK"),
            @ApiResponse(responseCode = "404", description = "Cannot delete user"),
            @ApiResponse(responseCode = "500", description = "Internal server error in the users"),
    })
    public ResponseEntity<HttpStatus> delete(
            @PathVariable("id")
            @Parameter(description = "User id", example = "777") Long id
    ) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
