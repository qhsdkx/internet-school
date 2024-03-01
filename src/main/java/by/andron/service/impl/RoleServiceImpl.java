package by.andron.service.impl;

import by.andron.dto.RoleCreationDto;
import by.andron.exception.ServiceException;
import by.andron.mapper.RoleMapper;
import by.andron.model.Role;
import by.andron.service.RoleService;
import lombok.RequiredArgsConstructor;
import by.andron.dto.RoleDto;
import by.andron.repository.RoleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper;

    public RoleDto findById(Long id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new ServiceException("Cannot find role by id in service", HttpStatus.BAD_REQUEST));
        return roleMapper.toDto(role);
    }

    public List<RoleDto> findAll(int page, int size) {
        try {
            return roleRepository.findAll(page, size).stream()
                    .map(roleMapper::toDto).toList();
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
            roleRepository.update(id, roleMapper.toEntity(roleCreationDto));
        } catch (Exception e) {
            throw new ServiceException("Cannot update this user in service", HttpStatus.BAD_REQUEST);
        }
    }

    public void delete(Long id) {
        try {
            roleRepository.delete(id);
        } catch (Exception e) {
            throw new ServiceException("Cannot delete this user in service", HttpStatus.BAD_REQUEST);
        }
    }

}
