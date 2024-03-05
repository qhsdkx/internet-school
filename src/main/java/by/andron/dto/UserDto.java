package by.andron.dto;

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
public class UserDto {

    @NotNull
    private Long id;

    @NotEmpty(message = "Name is never equal null")
    @Size(min = 2, max = 32, message = "Your login should be in diapazon from 2 to 32 characters")
    private String name;

    @NotEmpty(message = "Surname is never equal null")
    @Size(min = 2, max = 32, message = "Your surname should be in diapazon from 2 to 32 characters")
    private String surname;

    @NotEmpty(message = "Login is never equal null")
    @Size(min = 5, max = 32, message = "Your login should be in diapazon from 5 to 32 characters")
    private String login;

    @NotNull
    private LocalDateTime birthDate;

}
