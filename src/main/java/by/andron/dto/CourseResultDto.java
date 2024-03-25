package by.andron.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Course Result Dto")
public class CourseResultDto {

    @NotNull
    @Schema(description = "Course result id", example = "777")
    private Long id;

    @NotNull
    @Positive
    @Schema(description = "Score of student", example = "10")
    private Integer score;

    @NotEmpty
    @Schema(description = "Teacher feedback", example = "Good boy, I like him")
    private String feedback;

    @NotNull
    @Schema(description = "Course end date", example = "2023-1-1-0-0")
    private LocalDateTime endDate;

    @NotNull
    @Positive
    @Schema(description = "User id", example = "777")
    private Long userId;

    @NotNull
    @Positive
    @Schema(description = "Course id", example = "777")
    private Long courseId;

}
