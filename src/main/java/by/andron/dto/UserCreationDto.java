package by.andron.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "User Creation Dto")
public class UserCreationDto {

    @NotEmpty
    @Size(min = 2, max = 32, message = "Your name should be in diapazon from 5 to 32 characters")
    @Schema(description = "User name", example = "John")
    private String name;

    @NotEmpty
    @Size(min = 2, max = 32, message = "Your name should be in diapazon from 5 to 32 characters")
    @Schema(description = "User surname", example = "Smith")
    private String surname;

    @NotEmpty
    @Size(min = 5, max = 32, message = "Your login should be in diapazon from 5 to 32 characters")
    @Schema(description = "User login", example = "Abubakr1234.5678")
    private String login;

    @NotEmpty
    @Size(min = 5, max = 32, message = "Your password should be in diapazon from 5 to 32 characters")
    @Schema(description = "User password", example = "John")
    private String password;

    @NotNull
    @Schema(description = "User birth date", example = "2004-8-6-0-0")
    private LocalDateTime birthDate;

    @NotNull
    @Positive
    @Schema(description = "User roles", example = "[1, 2, 5]")
    private List<Long> roleIds;

}
