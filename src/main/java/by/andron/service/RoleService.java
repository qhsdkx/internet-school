package by.andron.service;

import by.andron.dto.RoleCreationDto;
import by.andron.exception.ServiceException;
import by.andron.mapper.RoleMapper;
import by.andron.model.Role;
import lombok.RequiredArgsConstructor;
import by.andron.dto.RoleDto;
import by.andron.repository.RoleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper;

    public RoleDto findById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ServiceException("Cannot find role by id in service", HttpStatus.BAD_REQUEST));
        return roleMapper.toDto(role);
    }

    public List<RoleDto> findAll() {
        try {
            return roleMapper.toDto(roleRepository.findAll());
        } catch (Exception e) {
            throw new ServiceException("Cannot find all roles in service", HttpStatus.BAD_REQUEST);
        }
    }

    public RoleDto save(RoleCreationDto roleCreationDto) {
        try {
            Role entity = roleMapper.toEntity(roleCreationDto);
            return roleMapper.toDto(roleRepository.save(entity));
        } catch (Exception e) {
            throw new ServiceException("Cannot save this role in service", HttpStatus.BAD_REQUEST);
        }
    }

    public void update(Long id, RoleCreationDto roleCreationDto) {
        try {
            Role role = roleRepository.findById(id)
                    .orElseThrow(() -> new ServiceException("Cannot find role by id in service", HttpStatus.BAD_REQUEST));
            Role entity = roleMapper.toEntity(roleCreationDto);
            updateRole(role, entity);
            roleRepository.save(role);
        } catch (Exception e) {
            throw new ServiceException("Cannot update this user in service", HttpStatus.BAD_REQUEST);
        }
    }

    public void delete(Long id) {
        try {
            roleRepository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException("Cannot delete this user in service", HttpStatus.BAD_REQUEST);
        }
    }

    private void updateRole(Role role, Role source){
        role.setId(source.getId());
        role.setName(source.getName());
        role.setUsers(source.getUsers());
    }

}
