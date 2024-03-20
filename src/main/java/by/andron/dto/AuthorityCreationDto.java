package by.andron.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Authority Creation Dto")
public class AuthorityCreationDto {

    @NotEmpty(message = "Name can't be empty")
    @Size(min = 6, max = 32, message = "Name cannot be lower than 6 characters and bigger than 32 characters")
    @Schema(description = "Name of authority", example = "UPDATE_AUTHORITY")
    private String name;

}
