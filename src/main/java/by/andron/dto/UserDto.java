package by.andron.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "User Dto")
public class UserDto {

    @NotNull
    @Schema(description = "User id", example = "777")
    private Long id;

    @NotEmpty(message = "Name is never equal null")
    @Size(min = 2, max = 32, message = "Your login should be in diapazon from 2 to 32 characters")
    @Schema(description = "User name", example = "John")
    private String name;

    @NotEmpty(message = "Surname is never equal null")
    @Size(min = 2, max = 32, message = "Your surname should be in diapazon from 2 to 32 characters")
    @Schema(description = "User surname", example = "Smith")
    private String surname;

    @NotEmpty(message = "Login is never equal null")
    @Size(min = 5, max = 32, message = "Your login should be in diapazon from 5 to 32 characters")
    @Schema(description = "User login", example = "andruha.1234.8")
    private String login;

    @NotNull
    @Schema(description = "User birth date", example = "2003-4-1-0-0")
    private LocalDateTime birthDate;

}
