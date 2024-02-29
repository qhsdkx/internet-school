package by.andron.controller;

import by.andron.dto.RoleCreationDto;
import by.andron.dto.RoleDto;
import by.andron.service.RoleService;
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
@RequiredArgsConstructor
@RequestMapping("/roles")
public class RoleController {

    private final RoleService service;

    @GetMapping("/{id}")
    public RoleDto findById(@PathVariable("id") Long id){
        return service.findById(id);
    }

    @GetMapping
    public List<RoleDto> findAll(){
        return service.findAll();
    }

    @PostMapping
    public RoleDto save (RoleCreationDto dto){
        return service.save(dto);
    }

    @PutMapping("/{id}")
    public void update (@PathVariable("id") Long id, RoleCreationDto dto){
        service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete (@PathVariable("id") Long id){
        service.delete(id);
    }

}
