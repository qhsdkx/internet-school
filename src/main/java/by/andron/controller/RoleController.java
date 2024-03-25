package by.andron.controller;

import by.andron.dto.RoleCreationDto;
import by.andron.dto.RoleDto;
import by.andron.service.RoleService;
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
@RequestMapping(value = "/roles")
@Tag(name = "Role controller", description = "API for roles")
public class RoleController {

    private final RoleService service;

    @PreAuthorize("hasAnyAuthority('READ_ROLE')")
    @GetMapping("/{id}")
    @Operation(summary = "Find role by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Everything's OK"),
            @ApiResponse(responseCode = "404", description = "Cannot find role"),
            @ApiResponse(responseCode = "500", description = "Internal server error in the roles"),
    })
    public ResponseEntity<RoleDto> findById(
            @PathVariable("id")
            @Parameter(description = "Role id", example = "777") Long id
    ) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('READ_ROLE')")
    @GetMapping
    @Operation(summary = "Find all roles by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Everything's OK"),
            @ApiResponse(responseCode = "404", description = "Cannot find roles"),
            @ApiResponse(responseCode = "500", description = "Internal server error in the roles"),
    })
    public ResponseEntity<List<RoleDto>> findAll(){
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('CREATE_ROLE')")
    @PostMapping
    @Operation(summary = "Save role by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Everything's OK"),
            @ApiResponse(responseCode = "404", description = "Cannot save role"),
            @ApiResponse(responseCode = "500", description = "Internal server error in the roles"),
    })
    public ResponseEntity<RoleDto> save (
            @RequestBody
            @Valid
            @Parameter(description = "Fill up role", required = true) RoleCreationDto dto
    ) {
        return new ResponseEntity<>(service.save(dto), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority('UPDATE_ROLE')")
    @PutMapping(value = "/{id}")
    @Operation(summary = "Update role by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Everything's OK"),
            @ApiResponse(responseCode = "404", description = "Cannot update role"),
            @ApiResponse(responseCode = "500", description = "Internal server error in the roles"),
    })
    public ResponseEntity<HttpStatus> update (
            @PathVariable("id")
            @Parameter(description = "Role id", example = "777") Long id,
            @RequestBody
            @Valid
            @Parameter(description = "Fill up the new role", required = true) RoleCreationDto dto
    ) {
        service.update(id, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('DELETE_ROLE')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete role by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Everything's OK"),
            @ApiResponse(responseCode = "404", description = "Cannot delete role"),
            @ApiResponse(responseCode = "500", description = "Internal server error in the roles"),
    })
    public ResponseEntity<HttpStatus> delete (
            @PathVariable("id")
            @Parameter(description = "Role id", example = "777") Long id
    ) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
