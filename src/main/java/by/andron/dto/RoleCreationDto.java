package by.andron.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Role Creation Dto")
public class RoleCreationDto {

    @NotNull
    @Size(min = 2, max = 16, message = "This role should be in diapazon from 2 to 16 characters")
    @Schema(description = "Role name", example = "Admin")
    private String name;

}
