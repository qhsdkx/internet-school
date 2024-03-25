package by.andron.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Course Dto")
public class CourseDto {

    @NotNull
    @Schema(description = "Course id", example = "777")
    private Long id;

    @NotEmpty
    @Size(min = 2, max = 16, message = "This course name should be in diapazon from 2 to 16 characters")
    @Schema(description = "Course name", example = "Math")
    private String name;

    @NotNull
    @Schema(description = "Course id", example = "777")
    private LocalDateTime createDate;

    @NotNull
    @FutureOrPresent
    @Schema(description = "Expiration Date", example = "2023-1-1-0-0")
    private LocalDateTime expirationDate;

    @NotNull
    @Positive
    @Schema(description = "Course teacher's id", example = "777")
    private Long teacherId;

}
