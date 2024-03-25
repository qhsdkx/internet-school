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
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Course Creation Dto")
public class CourseCreationDto {

    @NotEmpty
    @Size(min = 2, max = 16, message = "This course name should be in diapazon from 2 to 16 characters")
    @Schema(description = "Course name", example = "Math")
    private String name;

    @NotNull
    @Schema(description = "Create Date", example = "2023-1-1-0-0")
    private LocalDateTime createDate;

    @NotNull
    @FutureOrPresent
    @Schema(description = "Expiration Date", example = "2023-1-1-0-0")
    private LocalDateTime expirationDate;

    @NotEmpty
    @Size(min = 2, max = 16, message = "Course status should be in diapazon from 2 to 16 characters")
    @Schema(description = "Course status", example = "Passed")
    private String status;

    @NotNull
    @Positive
    @Schema(description = "Course teacher's id", example = "777")
    private Long teacherId;

}
