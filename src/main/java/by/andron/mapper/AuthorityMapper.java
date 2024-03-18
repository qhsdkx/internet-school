package by.andron.mapper;

import by.andron.dto.AuthorityCreationDto;
import by.andron.dto.AuthorityDto;
import by.andron.model.Authority;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "SPRING", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuthorityMapper {

    Authority toEntity(AuthorityCreationDto dto);

    AuthorityDto toDto(Authority authority);

    List<AuthorityDto> toDto(List<Authority> authorities);

}
