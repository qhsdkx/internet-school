package by.andron.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Role Dto")
@Builder
public class RoleDto {

    @NotNull
    @Schema(description = "Role id", example = "777")
    private Long id;

    @NotNull
    @Size(min = 2, max = 16, message = "This role should be in diapazon from 2 to 16 characters")
    @Schema(description = "Role name", example = "Admin")
    private String name;

}
