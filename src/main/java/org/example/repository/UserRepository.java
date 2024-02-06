package org.example.repository;

import org.example.exception.RepositoryException;
import org.example.model.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private final DataSource dataSource;

    public static final String SELECT_BY_ID_QUERY = "SELECT * FROM users WHERE id = ?";
    public static final String SELECT_QUERY = "SELECT * FROM users";
    public static final String INSERT_QUERY = "INSERT INTO users(name, surname, login, password) VALUES (?, ?, ?, ?)";
    public static final String UPDATE_BY_ID_QUERY = "UPDATE users SET name = ?, surname = ?, login = ?, password = ?, birth_day = ? WHERE id = ?";
    public static final String DELETE_BY_ID_QUERY = "DELETE FROM users WHERE id = ?";
    public static final String DELETE_USER_ROLE_LINK_QUERY = "DELETE FROM user_role_links WHERE user_id = ?";
    public static final String DELETE_USER_COURSE_LINK_QUERY = "DELETE FROM user_course_links WHERE user_id = ?";
    public static final String DELETE_USER_COURSE_RESULT_LINK_QUERY = "DELETE FROM course_results WHERE user_id = ?";

    public UserRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public User findById(Long id) {
        User user = new User();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID_QUERY)) {
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                fillUser(user, rs);
            }
        } catch (Exception e) {
            throw new RepositoryException("Cannot find user by id");
        }
        return user;
    }

    public List<User> findAll() {
        List<User> users = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY)) {

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                User user = new User();
                fillUser(user, rs);
                users.add(user);
            }
        } catch (Exception e) {
            throw new RepositoryException("Cannot find all users");
        }
        return users;
    }

    public User insert(User user) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getLogin());
            preparedStatement.setString(4, user.getPassword());
            int value = preparedStatement.executeUpdate();
            if (value == 1) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getLong(1));
                }
            }
            return user;
        } catch (Exception e) {
            throw new RepositoryException("Cannot insert user in table");
        }
    }

    public void updateUser(Long id, User user) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BY_ID_QUERY)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getLogin());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setDate(5, Date.valueOf(user.getBirthDay()));
            preparedStatement.setLong(6, id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new RepositoryException("Cannot update user by id");
        }
    }

    public void delete(Long id) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement deleteUser = connection.prepareStatement(DELETE_BY_ID_QUERY);
             PreparedStatement deleteUserRoleLinks = connection.prepareStatement(DELETE_USER_ROLE_LINK_QUERY);
             PreparedStatement deleteUserCourseLinks = connection.prepareStatement(DELETE_USER_COURSE_LINK_QUERY);
             PreparedStatement deleteUserCourseResultLinks = connection.prepareStatement(DELETE_USER_COURSE_RESULT_LINK_QUERY)) {
            try {
                connection.setAutoCommit(false);
                deleteUserRoleLinks.setLong(1, id);
                deleteUserRoleLinks.executeUpdate();
                deleteUserCourseLinks.setLong(1, id);
                deleteUserCourseLinks.executeUpdate();
                deleteUserCourseResultLinks.setLong(1, id);
                deleteUserCourseResultLinks.executeUpdate();
                deleteUser.setLong(1, id);
                deleteUser.executeUpdate();
                connection.commit();
            } catch (Exception e) {
                connection.rollback();
                throw new RepositoryException("User with id " + id + " was not deleted");
            } finally {
                connection.setAutoCommit(true);
            }
        }
    }

    private void fillUser(User user, ResultSet rs) throws SQLException {
        user.setId(rs.getLong("id"));
        user.setName(rs.getString("name"));
        user.setSurname(rs.getString("surname"));
        user.setLogin(rs.getString("login"));
        user.setPassword(rs.getString("password"));
    }

}
