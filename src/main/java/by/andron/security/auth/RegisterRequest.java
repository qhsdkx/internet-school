package by.andron.security.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String name;

    private String surname;

    private String password;

    private String login;

    private LocalDate birthDay;

}
