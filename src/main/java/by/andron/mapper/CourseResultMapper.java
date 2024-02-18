package by.andron.mapper;

import by.andron.dto.CourseResultCreationDto;
import by.andron.dto.CourseResultDto;
import by.andron.model.Course;
import by.andron.model.CourseResult;
import by.andron.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CourseResultMapper {

    @Mapping(target = "user", source = "userId", qualifiedByName = "mapUserIdToUser")
    @Mapping(target = "course", source = "courseId", qualifiedByName = "mapCourseIdToCourse")
    CourseResult toEntity(CourseResultCreationDto dto);

    @Mapping(target = "userId", source = "user", qualifiedByName = "mapUserToUserId")
    @Mapping(target = "courseId", source = "course", qualifiedByName = "mapCourseToCourseId")
    CourseResultDto toDto(CourseResult courseResult);

    @Named("mapUserIdToUser")
    default User mapUserIdToUser(Long userId) {
        User user = new User();
        user.setId(userId);
        return user;
    }

    @Named("mapUserToUserId")
    default Long mapUserToUserId(User user) {
        return user.getId();
    }

    @Named("mapCourseIdToCourse")
    default Course mapCourseIdToCourse(Long courseId) {
        Course course = new Course();
        course.setId(courseId);
        return course;
    }

    @Named("mapCourseToCourseId")
    default Long mapCourseToCourseId(Course course) {
        return course.getId();
    }

}
