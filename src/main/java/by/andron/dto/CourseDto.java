package by.andron.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto {

    @NotNull
    private Long id;

    @NotEmpty
    @Size(min = 2, max = 16, message = "This course name should be in diapazon from 2 to 16 characters")
    private String name;

    @NotNull
    private LocalDateTime createDate;

    @NotNull
    @FutureOrPresent
    private LocalDateTime expirationDate;

    @NotNull
    @Positive
    private Long teacherId;

}
