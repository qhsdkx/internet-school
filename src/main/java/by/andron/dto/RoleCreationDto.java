package by.andron.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleCreationDto {

    @NotNull
    @Size(min = 2, max = 16, message = "This role should be in diapazon from 2 to 16 characters")
    private String name;

}
