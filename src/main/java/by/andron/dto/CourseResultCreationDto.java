package by.andron.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseResultCreationDto {

    private Integer score;

    private String feedback;

    private LocalDateTime endDate;

    private Long userId;

    private Long courseId;

}
