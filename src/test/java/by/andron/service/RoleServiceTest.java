package by.andron.service;

import by.andron.dto.RoleCreationDto;
import by.andron.dto.RoleDto;
import by.andron.mapper.RoleMapper;
import by.andron.model.Authority;
import by.andron.model.Role;
import by.andron.model.User;
import by.andron.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest
public class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @Spy
    private RoleMapper roleMapper = Mappers.getMapper(RoleMapper.class);

    @InjectMocks
    private RoleService roleService;

    @Test
    @DisplayName("Find by id service test")
    void findByIdTest_shouldReturnRoleDto() {
        Long id = 2L;
        RoleDto expected = RoleDto.builder().id(id).name("Admin").build();

        when(roleRepository.findById(id)).thenReturn(Optional.of(Role.builder()
                .id(id)
                .authorities(new HashSet<>() {{
                    add(Authority.builder().id(2L).build());
                }})
                .users(new HashSet<>() {{
                    add(User.builder().id(69L).build());
                }})
                .name("Admin")
                .build()));
        RoleDto actual = roleService.findById(id);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Find all service test")
    void findAllTest_shouldReturnListOfRoleDto() {

        List<RoleDto> expected = new ArrayList<>() {{
            add(RoleDto.builder().id(1L).build());
            add(RoleDto.builder().id(2L).build());
            add(RoleDto.builder().id(3L).build());
        }};
        List<Role> roles = new ArrayList<>() {{
            add(Role.builder().id(1L).build());
            add(Role.builder().id(2L).build());
            add(Role.builder().id(3L).build());
        }};

        when(roleRepository.findAll());
        List<RoleDto> actual = roleService.findAll();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Add service test")
    void addTest_shouldReturnAddedRole() {
        RoleDto expected = RoleDto.builder().id(4L).name("Bratishka").build();
        Role roleWithoutId = Role.builder().name("Bratishka").build();
        Role roleWithId = Role.builder().id(4L).name("Bratishka").build();

        when(roleRepository.save(roleWithoutId)).thenReturn(roleWithId);
        RoleDto actual = roleService.save(new RoleCreationDto("Bratishka"));

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Update by id service test")
    void updateTest_shouldUpdateRole() {
        Long id = 4L;
        RoleCreationDto roleCreationDto = new RoleCreationDto("New bratishka");
        Role role = Role.builder().id(id).name("New bratishka").build();

        when(roleRepository.findById(id)).thenReturn(Optional.of(role));
        roleService.update(id, roleCreationDto);

        verify(roleRepository, times(1)).findById(id);
        verify(roleRepository, times(1)).save(role);
    }

    @Test
    @DisplayName("Delete by id service test")
    void deleteTest_shouldDeleteRole() {
        Long id = 4L;
        roleService.delete(id);

        verify(roleRepository, times(1)).deleteById(id);
    }

}

