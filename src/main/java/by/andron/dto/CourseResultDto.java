package by.andron.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseResultDto {

    @NotNull
    private Long id;

    @NotNull
    @Positive
    private Integer score;

    @NotEmpty
    private String feedback;

    @NotNull
    private LocalDateTime endDate;

    @NotNull
    @Positive
    private Long userId;

    @NotNull
    @Positive
    private Long courseId;

}
