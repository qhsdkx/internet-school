package by.andron.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreationDto {

    private String name;

    private String surname;

    private String login;

    private String password;

    private LocalDateTime birthDate;

    private List<Long> roleIds;

}
