package by.andron.mapper;

import by.andron.dto.RoleCreationDto;
import by.andron.dto.RoleDto;
import by.andron.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "SPRING", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper {

    Role toEntity(RoleCreationDto dto);

    RoleDto toDto(Role role);

}
