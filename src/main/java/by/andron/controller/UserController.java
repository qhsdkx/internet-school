package by.andron.controller;

import by.andron.dto.UserCreationDto;
import by.andron.dto.UserDto;
import by.andron.service.UserService;
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
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PreAuthorize("hasAuthority('READ_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable("id") Long id){
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('READ_USER')")
    @GetMapping
    public ResponseEntity<List<UserDto>> findAll(){
        return new ResponseEntity<> (service.findAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('CREATE_USER')")
    @PostMapping
    public ResponseEntity<UserDto> save (@RequestBody UserCreationDto userCreationDto){
        return new ResponseEntity<> (service.save(userCreationDto), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('UPDATE_USER')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> update (@PathVariable("id") Long id, @RequestBody UserCreationDto userCreationDto){
        service.update(id, userCreationDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('DELETE_USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id){
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
