package by.andron.controller;

import by.andron.dto.CourseResultCreationDto;
import by.andron.dto.UserCreationDto;
import by.andron.dto.UserDto;
import by.andron.service.UserService;
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
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping("/{id}")
    public UserDto findById(@PathVariable("id") Long id){
        return service.findById(id);
    }

    @GetMapping
    public List<UserDto> findAll(){
        return service.findAll();
    }

    @PostMapping
    public UserDto save (UserCreationDto userCreationDto){
        return service.save(userCreationDto);
    }

    @PutMapping("/{id}")
    public void update (@PathVariable("id") Long id, UserCreationDto userCreationDto){
        service.update(id, userCreationDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id){
        service.delete(id);
    }

}
