package by.andron.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseCreationDto {

    private String name;

    private LocalDateTime createDate;

    private LocalDateTime expirationDate;

    private String status;

    private Long teacherId;

}
