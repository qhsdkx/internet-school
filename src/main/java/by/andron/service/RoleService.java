package by.andron.service;

import by.andron.dto.RoleCreationDto;
import by.andron.mapper.RoleMapper;
import by.andron.model.Role;
import lombok.RequiredArgsConstructor;
import by.andron.dto.RoleDto;
import by.andron.repository.RoleRepository;

import java.util.List;

@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper;

    public RoleDto findById(Long id) {
        return roleMapper.toDto(roleRepository.findById(id));
    }

    public List<RoleDto> findAll() {
        return roleRepository.findAll().stream()
                .map(roleMapper::toDto).toList();
    }

    public RoleDto save(RoleCreationDto roleCreationDto) {
        Role entity = roleMapper.toEntity(roleCreationDto);
        return roleMapper.toDto(roleRepository.save(entity));
    }

    public void update(RoleCreationDto roleCreationDto) {
        roleRepository.update(roleMapper.toEntity(roleCreationDto));
    }

    public void delete(Long id) {
        roleRepository.delete(id);
    }

}
