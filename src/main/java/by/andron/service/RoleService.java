package by.andron.service;

import by.andron.dto.RoleCreationDto;
import by.andron.dto.RoleDto;

import java.util.List;

public interface RoleService {
    RoleDto findById(Long id);
    List<RoleDto> findAll();
    RoleDto save(RoleCreationDto roleCreationDto);
    void update(Long id, RoleCreationDto roleCreationDto);
    void delete(Long id);
}
