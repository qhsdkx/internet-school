package by.andron.repository;

import by.andron.model.CourseResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CourseResultRepository extends JpaRepository<CourseResult, Long> {

}
