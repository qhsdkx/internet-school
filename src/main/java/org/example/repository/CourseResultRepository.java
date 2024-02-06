package org.example.repository;

import org.example.exception.RepositoryException;
import org.example.model.CourseResult;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CourseResultRepository {
    private final DataSource dataSource;

    public static final String SELECT_BY_ID_QUERY = "SELECT * FROM course_results WHERE id = ?";
    public static final String SELECT_QUERY = "SELECT * FROM course_results";
    public static final String INSERT_QUERY = "INSERT INTO course_results(score, feedback, end_date, user_id) VALUES (?, ?, ?, ?)";
    public static final String UPDATE_BY_ID_QUERY = "UPDATE course_results SET score = ?, feedbackk = ?, end_date = ?, user_id = ? WHERE id = ?";
    public static final String DELETE_BY_ID_QUERY = "DELETE FROM course_results WHERE id = ?";

    public CourseResultRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public CourseResult findById(Long id) {
        CourseResult courseResult = new CourseResult();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID_QUERY)) {
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                fillCourseResult(courseResult, rs);
            }
        } catch (Exception e) {
            throw new RepositoryException("Cannot find course result by id");
        }
        return courseResult;
    }

    public List<CourseResult> findAll() {
        List<CourseResult> courseResults = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                CourseResult courseResult = new CourseResult();
                fillCourseResult(courseResult, rs);
                courseResults.add(courseResult);
            }
        } catch (Exception e) {
            throw new RepositoryException("Cannot find all course results");
        }
        return courseResults;
    }

    public CourseResult insert(CourseResult courseResult) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, courseResult.getScore());
            preparedStatement.setString(2, courseResult.getFeedback());
            preparedStatement.setDate(3, Date.valueOf(courseResult.getEndDate()));
            preparedStatement.setLong(4, courseResult.getUserId());
            int value = preparedStatement.executeUpdate();
            if (value == 1) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    courseResult.setId(generatedKeys.getLong(1));
                }
            }
            return courseResult;
        } catch (Exception e) {
            throw new RepositoryException("Cannot insert course result");
        }
    }

    public void update(Long id, CourseResult courseResult) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BY_ID_QUERY)) {
            preparedStatement.setInt(1, courseResult.getScore());
            preparedStatement.setString(2, courseResult.getFeedback());
            preparedStatement.setDate(3, Date.valueOf(courseResult.getEndDate()));
            preparedStatement.setLong(4, courseResult.getUserId());
            preparedStatement.setLong(6, id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new RepositoryException("Cannot update course result");
        }
    }

    public void delete(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID_QUERY)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new RepositoryException("Cannot delete course result by id");
        }
    }

    private void fillCourseResult(CourseResult courseResult, ResultSet rs) throws SQLException {
        courseResult.setId(rs.getLong("id"));
        courseResult.setScore(rs.getInt("score"));
        courseResult.setFeedback(rs.getString("feedback"));
        courseResult.setEndDate(LocalDate.parse(rs.getString("end_date")));
        courseResult.setUserId(rs.getLong("user_id"));
    }

}
