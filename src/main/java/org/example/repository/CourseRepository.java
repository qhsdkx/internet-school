package org.example.repository;

import org.example.exception.RepositoryException;
import org.example.model.Course;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CourseRepository {
    private final DataSource dataSource;

    public static final String SELECT_BY_ID_QUERY = "SELECT * FROM courses WHERE id = ?";
    public static final String SELECT_QUERY = "SELECT * FROM courses";
    public static final String INSERT_QUERY = "INSERT INTO courses(name, create_date, expiration_date, status, teacher_id) VALUES (?, ?, ?, ?, ?)";
    public static final String UPDATE_BY_ID_QUERY = "UPDATE courses SET name = ?, create_date = ?, expiration_date = ?, status = ?, teacher_id = ? WHERE id = ?";
    public static final String DELETE_BY_ID_QUERY = "DELETE FROM courses WHERE id = ?";
    public static final String DELETE_USER_COURSE_LINK_QUERY = "DELETE FROM user_course_links WHERE course_id = ?";

    public CourseRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Course findById(Long id) {
        Course course = new Course();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID_QUERY)) {

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                fillCourse(course, rs);
            }
        } catch (Exception e) {
            throw new RepositoryException("Cannot find course by id");
        }
        return course;
    }

    public List<Course> findAll() {
        List<Course> courses = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY)) {

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Course course = new Course();
                fillCourse(course, rs);
                courses.add(course);
            }
        } catch (Exception e) {
            throw new RepositoryException("Cannot find all courses");
        }
        return courses;
    }

    public Course insert(Course course) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, course.getName());
            preparedStatement.setDate(2, Date.valueOf(course.getCreateDate()));
            preparedStatement.setDate(3, Date.valueOf(course.getExpirationDate()));
            preparedStatement.setString(4, course.getStatus());
            preparedStatement.setLong(5, course.getTeacherId());
            int value = preparedStatement.executeUpdate();
            if (value == 1) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    course.setId(generatedKeys.getLong(1));
                }
            }
            return course;
        } catch (Exception e) {
            throw new RepositoryException("Cannot insert course in table");
        }
    }

    public void update(Long id, Course course) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BY_ID_QUERY)) {
            preparedStatement.setString(1, course.getName());
            preparedStatement.setDate(2, Date.valueOf(course.getCreateDate()));
            preparedStatement.setDate(3, Date.valueOf(course.getExpirationDate()));
            preparedStatement.setString(4, course.getStatus());
            preparedStatement.setLong(5, course.getTeacherId());
            preparedStatement.setLong(6, id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new RepositoryException("Cannot update course");
        }
    }

    public void delete(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement deleteCourse = connection.prepareStatement(DELETE_BY_ID_QUERY);
             PreparedStatement deleteUserCourseLink = connection.prepareStatement(DELETE_USER_COURSE_LINK_QUERY)) {
            try {
                connection.setAutoCommit(false);
                deleteUserCourseLink.setLong(1, id);
                deleteUserCourseLink.executeUpdate();
                deleteCourse.setLong(1, id);
                deleteCourse.executeUpdate();
                connection.commit();
            } catch (Exception e) {
                connection.rollback();
                throw new RepositoryException("Something went wrong with course deleting");
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (Exception e) {
            throw new RepositoryException("Cannot delete course");
        }
    }

    private void fillCourse(Course course, ResultSet rs) throws SQLException {
        course.setId(rs.getLong("id"));
        course.setName(rs.getString("name"));
        course.setCreateDate(LocalDate.parse(rs.getDate("create_date").toString()));
        course.setExpirationDate(LocalDate.parse(rs.getDate("expiration_date").toString()));
        course.setStatus(rs.getString("status"));
        course.setTeacherId(rs.getLong("teacher_id"));
    }

}
