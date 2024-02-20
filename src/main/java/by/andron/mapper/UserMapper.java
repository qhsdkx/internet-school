package by.andron.mapper;

import by.andron.dto.UserCreationDto;
import by.andron.dto.UserDto;
import by.andron.model.Role;
import by.andron.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "SPRING", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "roles", source = "roleIds", qualifiedByName = "mapRoleIdsToRoles")
    User toEntity(UserCreationDto userDto);

    UserDto toDto(User user);

    List<UserDto> toDto(List<User> user);

    @Named("mapRoleIdsToRoles")
    default Set<Role> mapRoleIdsToRoles(List<Long> roleIds) {
        return roleIds.stream()
                .map(roleId -> {
                    Role role = new Role();
                    role.setId(roleId);
                    return role;
                }).collect(Collectors.toSet());
    }

    @Named("mapRolesToRoleIds")
    default List<Long> mapRolesToRoleIds(Set<Role> roles) {
        return roles.stream()
                .map(Role::getId)
                .toList();
    }

}
