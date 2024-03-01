package by.andron.service.impl;

import by.andron.dto.RoleCreationDto;
import by.andron.mapper.RoleMapper;
import by.andron.model.Role;
import by.andron.service.RoleService;
import lombok.RequiredArgsConstructor;
import by.andron.dto.RoleDto;
import by.andron.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

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

    public void update(Long id, RoleCreationDto roleCreationDto) {
        roleRepository.update(id, roleMapper.toEntity(roleCreationDto));
    }

    public void delete(Long id) {
        roleRepository.delete(id);
    }

}
