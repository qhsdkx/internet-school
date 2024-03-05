package by.andron.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreationDto {

    @NotEmpty
    @Size(min = 2, max = 32, message = "Your name should be in diapazon from 5 to 32 characters")
    private String name;

    @NotEmpty
    @Size(min = 2, max = 32, message = "Your name should be in diapazon from 5 to 32 characters")
    private String surname;

    @NotEmpty
    @Size(min = 5, max = 32, message = "Your login should be in diapazon from 5 to 32 characters")
    private String login;

    @NotEmpty
    @Size(min = 5, max = 32, message = "Your password should be in diapazon from 5 to 32 characters")
    private String password;

    @NotNull
    private LocalDateTime birthDate;

    @NotNull
    @Positive
    private List<Long> roleIds;

}
